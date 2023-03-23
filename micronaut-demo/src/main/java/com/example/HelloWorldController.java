package com.example;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Produces;
import io.reactivex.Single;
import io.vertx.reactivex.pgclient.PgPool;
import io.vertx.reactivex.sqlclient.Row;
import io.vertx.reactivex.sqlclient.RowIterator;
import jakarta.inject.Inject;

@Controller("/postgres")
public class HelloWorldController {

    @Inject
    PgPool pgClient;

    @Get("/hello")
    @Produces(MediaType.TEXT_PLAIN)
    public String index() {
        return "Hello World";
    }

    @Get
    @Produces(MediaType.TEXT_PLAIN)
    public Single<HttpResponse> getPostgresVersion() {
        return  pgClient.query("SELECT version()")
                .rxExecute()
                .map(rows -> {
                    RowIterator<Row> rowIt = rows.iterator();
                    if (rowIt.hasNext()) {
                        String version = rowIt.next().getString("version");
                        HttpResponse.ok(version);
                    }

                    return HttpResponse.notFound();
                });
    }
}
