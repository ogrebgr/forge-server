package com.bolyartech.forge.server.db;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.sql.Connection;
import java.sql.SQLException;


public final class C3p0DbPool implements DbPool {
    private final ComboPooledDataSource mDbPool;


    public C3p0DbPool(ComboPooledDataSource dbPool) {
        mDbPool = dbPool;
    }


    @Override
    public Connection getConnection() throws SQLException {
        return mDbPool.getConnection();
    }
}
