package com.example;

import org.jdbi.v3.sqlobject.statement.SqlQuery;

public interface VersionRepository {
    @SqlQuery("SELECT version()")
    String getVersion();
}
