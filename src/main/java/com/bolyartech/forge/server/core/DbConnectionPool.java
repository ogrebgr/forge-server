package com.bolyartech.forge.server.core;

/**
 * Created by ogre on 11.11.15.
 */
public interface DbConnectionPool {
    java.sql.Connection getConnection() throws java.sql.SQLException;
}
