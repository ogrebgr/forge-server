package com.bolyartech.forge.server.db;

/**
 * Defines interface for DB configuration
 */
public interface DbConfiguration {
    /**
     * Returns DB DSN
     *
     * @return DSN
     */
    String getDbDsn();

    /**
     * Returns DB username
     *
     * @return username
     */
    String getDbUsername();

    /**
     * Returns DB password
     *
     * @return password
     */
    String getDbPassword();


    /**
     * Returns maximum statements to be cached
     *
     * @return maximum statements to be cached
     */
    int getCacheMaxStatements();

    /**
     * Returns initial pool size
     *
     * @return initial pool size
     */
    int getInitialPoolSize();

    /**
     * Return minimum pool size
     *
     * @return minimum pool size
     */
    int getMinPoolSize();

    /**
     * Returns maximum pool size
     *
     * @return maximum pool size
     */
    int getMaxPoolSize();

    /**
     * Returns idle connection test period
     *
     * @return How often connections will be tested if they are broken or stale
     */
    int getIdleConnectionTestPeriod();

    /**
     * Returns setting for testing connection on checkin
     *
     * @return setting for testing connection on checkin
     */
    boolean getTestConnectionOnCheckin();

    /**
     * Returns setting for testing connection on checkout
     *
     * @return setting for testing connection on checkout
     */
    boolean getTestConnectionOnCheckout();
}
