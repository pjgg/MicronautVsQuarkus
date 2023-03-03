package com.exmaple;

import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.QuarkusApplication;
import io.quarkus.runtime.annotations.QuarkusMain;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.impl.RouterImpl;
import io.vertx.pgclient.PgPool;
import io.vertx.sqlclient.Row;
import io.vertx.sqlclient.RowIterator;
import org.eclipse.microprofile.config.ConfigProvider;
import org.jboss.logging.Logger;

import javax.enterprise.inject.spi.CDI;


@QuarkusMain
public class Main implements QuarkusApplication {

    private static final Logger LOG = Logger.getLogger(Main.class);

    public static void main(String... args) {
        Quarkus.run(Main.class, args);
    }

    @Override
    public int run(String... args) {
        PgPool pgClient = CDI.current().select(PgPool.class).get();
        Vertx vertx = CDI.current().select(Vertx.class).get();
        Router router = new RouterImpl(vertx);

        String message = ConfigProvider.getConfig().getValue("message", String.class);
        int serverPort = ConfigProvider.getConfig().getValue("server.port", Integer.class);

        router.get("/").handler(rc -> rc.response().end(message));

        router.get("/postgres")
                .handler(rc -> pgClient.query("SELECT version()")
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

        HttpServer server = vertx.createHttpServer()
                .requestHandler(router)
                .listen(serverPort)
                .onFailure(e -> LOG.errorf("Vertx server start failed: %s", e.getMessage()))
                .onSuccess(s -> LOG.infof("Vertx server started ::%d", serverPort))
                .result();

        Quarkus.waitForExit();
        server.close();
        return 0;
    }
}
