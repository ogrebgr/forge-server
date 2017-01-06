package com.bolyartech.forge.server.db;


public class DbConfigurationImpl implements DbConfiguration {
    private final String mDbDsn;
    private final String mDbUsername;
    private final String mDbPassword;

    private final int mMaxStatements;
    private final int mInitialPoolSize;
    private final int mMinPoolSize;
    private final int mMaxPoolSize;
    private final int mIdleConnectionTestPeriod;
    private final boolean mTestConnectionOnCheckin;
    private final boolean mTestConnectionOnCheckout;


    public DbConfigurationImpl(String dbDsn,
                               String dbUsername,
                               String dbPassword,
                               int maxStatements,
                               int initialPoolSize,
                               int minPoolSize,
                               int maxPoolSize,
                               int idleConnectionTestPeriod,
                               boolean testConnectionOnCheckin,
                               boolean testConnectionOnCheckout) {

        mDbDsn = dbDsn;
        mDbUsername = dbUsername;
        mDbPassword = dbPassword;
        mMaxStatements = maxStatements;
        mInitialPoolSize = initialPoolSize;
        mMinPoolSize = minPoolSize;
        mMaxPoolSize = maxPoolSize;
        mIdleConnectionTestPeriod = idleConnectionTestPeriod;
        mTestConnectionOnCheckin = testConnectionOnCheckin;
        mTestConnectionOnCheckout = testConnectionOnCheckout;
    }


    public String getDbDsn() {
        return mDbDsn;
    }


    public String getDbUsername() {
        return mDbUsername;
    }


    @Override
    public String getDbPassword() {
        return mDbPassword;
    }


    public int getMaxStatements() {
        return mMaxStatements;
    }


    public int getInitialPoolSize() {
        return mInitialPoolSize;
    }


    public int getMinPoolSize() {
        return mMinPoolSize;
    }


    public int getMaxPoolSize() {
        return mMaxPoolSize;
    }


    public int getIdleConnectionTestPeriod() {
        return mIdleConnectionTestPeriod;
    }


    @Override
    public boolean getTestConnectionOnCheckin() {
        return mTestConnectionOnCheckin;
    }


    @Override
    public boolean getTestConnectionOnCheckout() {
        return mTestConnectionOnCheckout;
    }
}
