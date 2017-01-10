package com.bolyartech.forge.server.db;

import java.sql.Connection;
import java.sql.SQLException;


/**
 * Database connection pool
 */
public interface DbPool {
    /**
     * Returns database connection
     *
     * @return database connection
     * @throws SQLException if error occur in the db driver
     */
    Connection getConnection() throws SQLException;
}
