package com.example;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Produces;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.impl.future.PromiseImpl;
import io.vertx.sqlclient.Row;
import io.vertx.sqlclient.RowIterator;
import io.vertx.sqlclient.SqlClient;

@Controller("/postgres")
public class HelloWorldController {

    private final SqlClient sqlClient;

    public HelloWorldController(SqlClient sqlClient) {
        this.sqlClient = sqlClient;
    }

    @Get("/hello")
    @Produces(MediaType.TEXT_PLAIN)
    public String index() {
        return "Hello World";
    }

    @Get
    @Produces(MediaType.TEXT_PLAIN)
    public HttpResponse getPostgresVersion() {

        return sqlClient.query("SELECT version()")
                .execute()
                .map(rows -> {
                    RowIterator<Row> rowIt = rows.iterator();
                    if (rowIt.hasNext()) {
                        String version = rowIt.next().getString("version");
                        return HttpResponse.ok(version);
                    }
                    return HttpResponse.notFound();
                }).result();
    }
}
