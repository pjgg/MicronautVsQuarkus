package com.example;

import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.Row;
import io.vertx.mutiny.sqlclient.RowSet;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/postgres")
public class Endpoint {

    @Inject
    PgPool pgClient;

    @GET
    public String hello() {
        RowSet<Row> rowSet =  pgClient.query("SELECT version()").execute().await().indefinitely();
        return rowSet.iterator().next().getString("version");
    }

    @GET
    @Path("/async")
    public Uni<String> helloAsync() {
        return pgClient.query("SELECT version()")
                .execute()
                .map(rowSet -> rowSet.iterator().next().getString("version"));
    }
}
