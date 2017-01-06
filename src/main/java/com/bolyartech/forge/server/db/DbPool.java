package com.bolyartech.forge.server.db;

import java.sql.Connection;
import java.sql.SQLException;


public interface DbPool {
    Connection getConnection() throws SQLException;
}
