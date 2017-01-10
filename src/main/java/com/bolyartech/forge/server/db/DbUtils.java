package com.bolyartech.forge.server.db;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;


/**
 * Utility class for DB related methods
 */
public class DbUtils {
    private DbUtils() {
        throw new AssertionError("Non-instantiable utility class");
    }


    /**
     * Checks if DB connection is operational, i.e. not closed
     *
     * @param dbc DB connection
     * @throws SQLException if DB error occur
     */
    public static void ensureOperationalDbc(Connection dbc) throws SQLException {
        if (dbc != null) {
            if (dbc.isClosed()) {
                throw new IllegalArgumentException("DB connection is closed.");
            }
        } else {
            throw new NullPointerException("dbc is null");
        }
    }


    /**
     * @param id Checks if id is > 0
     * @throws IllegalArgumentException If id is invalid, i.e. <= 0
     */
    public static void ensureValidId(long id) {
        if (id <= 0) {
            throw new IllegalArgumentException("invalid ID: " + id);
        }
    }


    /**
     * Creates C3P0 {@link DbPool}
     *
     * @param conf DB configuration
     * @return DB connection pool
     */
    public static DbPool createC3P0DbPool(DbConfiguration conf) {
        Properties p = new Properties(System.getProperties());
        p.put("com.mchange.v2.log.MLog", "com.mchange.v2.log.FallbackMLog");
        p.put("com.mchange.v2.log.FallbackMLog.DEFAULT_CUTOFF_LEVEL", "OFF");
        System.setProperties(p);

        ComboPooledDataSource cpds = new ComboPooledDataSource();
        cpds.setJdbcUrl(conf.getDbDsn());
        cpds.setUser(conf.getDbUsername());
        cpds.setPassword(conf.getDbPassword());
        cpds.setMaxStatements(conf.getCacheMaxStatements());
        cpds.setInitialPoolSize(conf.getInitialPoolSize());
        cpds.setMinPoolSize(conf.getMinPoolSize());
        cpds.setMaxPoolSize(conf.getMaxPoolSize());
        cpds.setIdleConnectionTestPeriod(conf.getIdleConnectionTestPeriod());
        cpds.setTestConnectionOnCheckin(conf.getTestConnectionOnCheckin());
        cpds.setTestConnectionOnCheckout(conf.getTestConnectionOnCheckout());
        cpds.setConnectionCustomizerClassName("com.bolyartech.forge.server.db.C3p0ConnectionCustomizer");

        return new C3p0DbPool(cpds);
    }
}
