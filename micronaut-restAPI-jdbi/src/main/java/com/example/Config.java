package com.example;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import io.micronaut.context.annotation.Factory;
import io.micronaut.context.annotation.Value;
import io.micronaut.core.annotation.ReflectionConfig;
import io.micronaut.core.annotation.TypeHint;
import jakarta.inject.Singleton;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.config.internal.ConfigCaches;
import org.jdbi.v3.sqlobject.SqlObjects;

import javax.sql.DataSource;

@ReflectionConfig(
        type = ConfigCaches.class,
        accessType = {
                TypeHint.AccessType.ALL_DECLARED_CONSTRUCTORS,
        }
)
@ReflectionConfig(
        type = org.jdbi.v3.core.statement.SqlStatements.class,
        accessType = {
                TypeHint.AccessType.ALL_DECLARED_CONSTRUCTORS,
        }
)
@ReflectionConfig(
        type = org.jdbi.v3.core.argument.Arguments.class,
        accessType = {
                TypeHint.AccessType.ALL_DECLARED_CONSTRUCTORS,
        }
)
@ReflectionConfig(
        type = org.jdbi.v3.core.qualifier.Qualifiers.class,
        accessType = {
                TypeHint.AccessType.ALL_DECLARED_CONSTRUCTORS,
        }
)
@ReflectionConfig(
        type = org.jdbi.v3.core.mapper.RowMappers.class,
        accessType = {
                TypeHint.AccessType.ALL_DECLARED_CONSTRUCTORS,
        }
)
@ReflectionConfig(
        type = org.jdbi.v3.core.mapper.ColumnMappers.class,
        accessType = {
                TypeHint.AccessType.ALL_DECLARED_CONSTRUCTORS,
        }
)
@ReflectionConfig(
        type = org.jdbi.v3.core.mapper.Mappers.class,
        accessType = {
                TypeHint.AccessType.ALL_DECLARED_CONSTRUCTORS,
        }
)
@ReflectionConfig(
        type = org.jdbi.v3.core.collector.JdbiCollectors.class,
        accessType = {
                TypeHint.AccessType.ALL_DECLARED_CONSTRUCTORS,
        }
)
@ReflectionConfig(
        type = org.jdbi.v3.core.extension.Extensions.class,
        accessType = {
                TypeHint.AccessType.ALL_DECLARED_CONSTRUCTORS,
        }
)
@ReflectionConfig(
        type = org.jdbi.v3.core.internal.OnDemandExtensions.class,
        accessType = {
                TypeHint.AccessType.ALL_DECLARED_CONSTRUCTORS,
        }
)
@ReflectionConfig(
        type = org.jdbi.v3.sqlobject.statement.internal.SqlObjectStatementConfiguration.class,
        accessType = {
                TypeHint.AccessType.ALL_DECLARED_CONSTRUCTORS,
        }
)
@ReflectionConfig(
        type = org.jdbi.v3.sqlobject.Handlers.class,
        accessType = {
                TypeHint.AccessType.ALL_DECLARED_CONSTRUCTORS,
        }
)
@ReflectionConfig(
        type = org.jdbi.v3.sqlobject.HandlerDecorators.class,
        accessType = {
                TypeHint.AccessType.ALL_DECLARED_CONSTRUCTORS,
        }
)
@ReflectionConfig(
        type = org.jdbi.v3.sqlobject.statement.internal.SqlQueryHandler.class,
        accessType = {
                TypeHint.AccessType.ALL_DECLARED_CONSTRUCTORS,
        }
)
@ReflectionConfig(
        type = org.jdbi.v3.core.mapper.reflect.internal.PojoTypes.class,
        accessType = {
                TypeHint.AccessType.ALL_DECLARED_CONSTRUCTORS,
        }
)
@ReflectionConfig(
        type = org.jdbi.v3.core.Handles.class,
        accessType = {
                TypeHint.AccessType.ALL_DECLARED_CONSTRUCTORS,
        }
)
@ReflectionConfig(
        type = SqlObjects.class,
        accessType = {
                TypeHint.AccessType.ALL_DECLARED_CONSTRUCTORS,
        }
)
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
        //TODO HikariConfig and / or DataSource are not injected in the context, so I have to instantiated manually
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
