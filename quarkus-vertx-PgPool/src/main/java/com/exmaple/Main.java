package com.exmaple;

import io.quarkus.runtime.Quarkus;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.pgclient.PgPool;
import io.vertx.sqlclient.Row;
import io.vertx.sqlclient.RowIterator;
import org.eclipse.microprofile.config.ConfigProvider;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;


@ApplicationScoped
public class Main {

    private static final Logger LOG = Logger.getLogger(Main.class);

    @Inject
    PgPool pgClient;

    void setupRouter(@Observes Router router) {
        String message = ConfigProvider.getConfig().getValue("message", String.class);

        router.get("/").handler(rc -> rc.response().end(message));

        router.get("/postgres").handler(rc -> pgClient.query("SELECT version()")
                .execute()
                .map(rows -> {
                    RowIterator<Row> rowIt = rows.iterator();
                    if (rowIt.hasNext()) {
                        String version = rowIt.next().getString("version");
                        return rc.response().end(JsonObject.of("version", version).encode());
                    }

                    return rc.response().setStatusCode(404).end();
                }));

        router.get("/bye").handler(rc -> {
            rc.response().end("bye");
            Quarkus.asyncExit();
        });
    }
}
