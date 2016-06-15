package com.bolyartech.forge.server.db;

import java.sql.Connection;
import java.sql.SQLException;

public class DbUtils {
    private DbUtils() {
        throw new AssertionError("Non-instantiable utility class");
    }


    public static void ensureOperationalDbc(Connection dbc) throws SQLException {
        if (dbc.isClosed()) {
            throw new IllegalArgumentException("DB connection is closed.");
        }
    }

    /**
     * @throws IllegalArgumentException If id is invalid, i.e. <= 0
     * @param id Checks if id is > 0
     */
    public static void ensureValidId(long id) {
        if (id <= 0) {
            throw new IllegalArgumentException("invalid ID: " + id);
        }
    }
}
