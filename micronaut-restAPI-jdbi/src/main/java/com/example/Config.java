package com.example;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import io.micronaut.context.annotation.Factory;
import io.micronaut.context.annotation.Value;
import jakarta.inject.Singleton;
import org.jdbi.v3.core.Jdbi;

import javax.sql.DataSource;

@Singleton
public class Config {

    @Value("${datasources.default.url}")
    String datasourceURL;

    @Value("${datasources.default.username}")
    String username;

    @Value("${datasources.default.password}")
    String password;

    @Value("${datasources.default.dialect}")
    String dialect;

    @Value("${datasources.default.driverClassName}")
    String driverClassName;

    @Factory
    public Jdbi getJdbi(DataSource datasource) {
        //TODO HikariConfig and / or DataSource are not injected on the context so I have to instantiated manually
        var config = new HikariConfig();
        config.setJdbcUrl(datasourceURL);
        config.setUsername(username);
        config.setPassword(password);
        config.setDriverClassName(driverClassName);

        var jdbi = Jdbi.create(new HikariDataSource(config));
        jdbi.installPlugins();
        return jdbi;
    }
}
