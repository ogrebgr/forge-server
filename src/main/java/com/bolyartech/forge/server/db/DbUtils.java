package com.bolyartech.forge.server.db;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import javax.annotation.Nonnull;
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
    public static void ensureOperationalDbc(@Nonnull Connection dbc) throws SQLException {
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
    public static DbPool createC3P0DbPool(@Nonnull DbConfiguration conf) {
        Properties p = new Properties(System.getProperties());
        //noinspection SpellCheckingInspection
        p.put("com.mchange.v2.log.MLog", "com.mchange.v2.log.FallbackMLog");
        //noinspection SpellCheckingInspection
        p.put("com.mchange.v2.log.FallbackMLog.DEFAULT_CUTOFF_LEVEL", "OFF");
        System.setProperties(p);

        ComboPooledDataSource comboPooledDataSource = new ComboPooledDataSource();
        comboPooledDataSource.setJdbcUrl(conf.getDbDsn());
        comboPooledDataSource.setUser(conf.getDbUsername());
        comboPooledDataSource.setPassword(conf.getDbPassword());
        comboPooledDataSource.setMaxStatements(conf.getCacheMaxStatements());
        comboPooledDataSource.setInitialPoolSize(conf.getInitialPoolSize());
        comboPooledDataSource.setMinPoolSize(conf.getMinPoolSize());
        comboPooledDataSource.setMaxPoolSize(conf.getMaxPoolSize());
        comboPooledDataSource.setIdleConnectionTestPeriod(conf.getIdleConnectionTestPeriod());
        comboPooledDataSource.setTestConnectionOnCheckin(conf.getTestConnectionOnCheckIn());
        comboPooledDataSource.setTestConnectionOnCheckout(conf.getTestConnectionOnCheckout());
        comboPooledDataSource.setConnectionCustomizerClassName("com.bolyartech.forge.server.db.C3p0ConnectionCustomizer");

        return new C3p0DbPool(comboPooledDataSource);
    }
}
