package com.bolyartech.forge.server.db;


/**
 * DB configuration
 */
public class DbConfigurationImpl implements DbConfiguration {
    private final String mDbDsn;
    private final String mDbUsername;
    private final String mDbPassword;

    private final int mCacheMaxStatements;
    private final int mInitialPoolSize;
    private final int mMinPoolSize;
    private final int mMaxPoolSize;
    private final int mIdleConnectionTestPeriod;
    private final boolean mTestConnectionOnCheckIn;
    private final boolean mTestConnectionOnCheckout;


    /**
     * Creates new DbConfigurationImpl
     *
     * @param dbDsn                    DB DSN
     * @param dbUsername               DB username
     * @param dbPassword               DB password
     * @param cacheMaxStatements       maximum statements to be cached
     * @param initialPoolSize          initial pool size
     * @param minPoolSize              minimum pool size
     * @param maxPoolSize              maximum pool size
     * @param idleConnectionTestPeriod idle connection test period
     * @param testConnectionOnCheckIn  setting for testing connection on check-in
     * @param testConnectionOnCheckout setting for testing connection on checkout
     */
    public DbConfigurationImpl(String dbDsn,
                               String dbUsername,
                               String dbPassword,
                               int cacheMaxStatements,
                               int initialPoolSize,
                               int minPoolSize,
                               int maxPoolSize,
                               int idleConnectionTestPeriod,
                               boolean testConnectionOnCheckIn,
                               boolean testConnectionOnCheckout) {

        mDbDsn = dbDsn;
        mDbUsername = dbUsername;
        mDbPassword = dbPassword;
        mCacheMaxStatements = cacheMaxStatements;
        mInitialPoolSize = initialPoolSize;
        mMinPoolSize = minPoolSize;
        mMaxPoolSize = maxPoolSize;
        mIdleConnectionTestPeriod = idleConnectionTestPeriod;
        mTestConnectionOnCheckIn = testConnectionOnCheckIn;
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


    public int getCacheMaxStatements() {
        return mCacheMaxStatements;
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
    public boolean getTestConnectionOnCheckIn() {
        return mTestConnectionOnCheckIn;
    }


    @Override
    public boolean getTestConnectionOnCheckout() {
        return mTestConnectionOnCheckout;
    }
}
