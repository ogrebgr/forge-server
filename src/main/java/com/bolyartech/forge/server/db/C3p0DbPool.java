package com.bolyartech.forge.server.db;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.sql.Connection;
import java.sql.SQLException;


/**
 * C3p0 implementation od {@link DbPool}
 */
public final class C3p0DbPool implements DbPool {
    private final ComboPooledDataSource mDataSource;


    /**
     * Creates new instance
     *
     * @param dataSource data source to be used
     */
    public C3p0DbPool(ComboPooledDataSource dataSource) {
        mDataSource = dataSource;
    }


    @Override
    public Connection getConnection() throws SQLException {
        return mDataSource.getConnection();
    }
}
