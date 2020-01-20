package com.bolyartech.forge.server.db;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import javax.annotation.Nonnull;
import java.sql.Connection;
import java.sql.SQLException;


/**
 * C3p0 implementation od {@link DbPool}
 */
public final class C3p0DbPool implements DbPool {
    private final ComboPooledDataSource dataSource;


    /**
     * Creates new instance
     *
     * @param dataSource data source to be used
     */
    public C3p0DbPool(@Nonnull ComboPooledDataSource dataSource) {
        this.dataSource = dataSource;
    }


    @Override
    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
}
