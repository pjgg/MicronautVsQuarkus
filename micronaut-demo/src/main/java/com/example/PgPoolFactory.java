package com.example;
import io.micronaut.context.annotation.Factory;
import io.vertx.pgclient.PgConnectOptions;
import io.vertx.pgclient.PgPool;
import io.vertx.sqlclient.PoolOptions;
import io.vertx.sqlclient.SqlClient;
import jakarta.inject.Singleton;

@Factory
public class PgPoolFactory {
    @Singleton
    SqlClient sqlClient() {
        PgConnectOptions connectOptions = new PgConnectOptions()
                .setPort(5432)
                .setHost("localhost")
                .setDatabase("mydb")
                .setUser("user")
                .setPassword("topsecret");
        PoolOptions poolOptions = new PoolOptions().setMaxSize(5);
        return PgPool.client(connectOptions, poolOptions);
    }
}
