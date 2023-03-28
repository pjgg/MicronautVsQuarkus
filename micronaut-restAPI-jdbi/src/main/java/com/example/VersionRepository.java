package com.example;

import io.micronaut.core.annotation.ReflectiveAccess;
import org.jdbi.v3.sqlobject.statement.SqlQuery;

@ReflectiveAccess
public interface VersionRepository {
    @SqlQuery("SELECT version()")
    String getVersion();
}
