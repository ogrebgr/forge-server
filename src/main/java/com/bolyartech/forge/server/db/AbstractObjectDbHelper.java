package com.bolyartech.forge.server.db;

import java.sql.Connection;


abstract public class AbstractObjectDbHelper implements ObjectDbHelper {
    private Connection mDbc;


    @Override
    public void init(Connection dbc) {
        mDbc = dbc;
    }


    @Override
    public Connection getDbc() {
        return mDbc;
    }


    @Override
    public Connection getConnection() {
        return mDbc;
    }


    @Override
    public void checkInitialized() throws IllegalStateException {
        if (mDbc == null) {
            throw new IllegalStateException("Not initialized. Did you forgot to call init()?");
        }
    }
}
