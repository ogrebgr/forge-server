package com.bolyartech.forge.server.db;

import java.sql.Connection;


public interface ObjectDbHelper {
    void init(Connection dbc);

    Connection getDbc();

    Connection getConnection();

    void checkInitialized() throws IllegalStateException;
}
