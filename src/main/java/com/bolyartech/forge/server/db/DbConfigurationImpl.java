package com.bolyartech.forge.server.db;


/**
 * DB configuration
 */
public class DbConfigurationImpl implements DbConfiguration {
    private final String dbDsn;
    private final String dbUsername;
    private final String dbPassword;

    private final int cacheMaxStatements;
    private final int initialPoolSize;
    private final int minPoolSize;
    private final int maxPoolSize;
    private final int idleConnectionTestPeriod;
    private final boolean testConnectionOnCheckIn;
    private final boolean testConnectionOnCheckout;


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

        this.dbDsn = dbDsn;
        this.dbUsername = dbUsername;
        this.dbPassword = dbPassword;
        this.cacheMaxStatements = cacheMaxStatements;
        this.initialPoolSize = initialPoolSize;
        this.minPoolSize = minPoolSize;
        this.maxPoolSize = maxPoolSize;
        this.idleConnectionTestPeriod = idleConnectionTestPeriod;
        this.testConnectionOnCheckIn = testConnectionOnCheckIn;
        this.testConnectionOnCheckout = testConnectionOnCheckout;
    }


    public String getDbDsn() {
        return dbDsn;
    }


    public String getDbUsername() {
        return dbUsername;
    }


    @Override
    public String getDbPassword() {
        return dbPassword;
    }


    public int getCacheMaxStatements() {
        return cacheMaxStatements;
    }


    public int getInitialPoolSize() {
        return initialPoolSize;
    }


    public int getMinPoolSize() {
        return minPoolSize;
    }


    public int getMaxPoolSize() {
        return maxPoolSize;
    }


    public int getIdleConnectionTestPeriod() {
        return idleConnectionTestPeriod;
    }


    @Override
    public boolean getTestConnectionOnCheckIn() {
        return testConnectionOnCheckIn;
    }


    @Override
    public boolean getTestConnectionOnCheckout() {
        return testConnectionOnCheckout;
    }
}
