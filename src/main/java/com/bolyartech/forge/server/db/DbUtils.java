package com.bolyartech.forge.server.db;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;


public class DbUtils {
    private DbUtils() {
        throw new AssertionError("Non-instantiable utility class");
    }


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


    public static DbPool createComboPooledDataSource(DbConfiguration conf) {
        Properties p = new Properties(System.getProperties());
        p.put("com.mchange.v2.log.MLog", "com.mchange.v2.log.FallbackMLog");
        p.put("com.mchange.v2.log.FallbackMLog.DEFAULT_CUTOFF_LEVEL", "OFF");
        System.setProperties(p);

        ComboPooledDataSource cpds = new ComboPooledDataSource();
        cpds.setJdbcUrl(conf.getDbDsn());
        cpds.setUser(conf.getDbUsername());
        cpds.setPassword(conf.getDbPassword());
        cpds.setMaxStatements(conf.getMaxStatements());
        cpds.setInitialPoolSize(conf.getInitialPoolSize());
        cpds.setMinPoolSize(conf.getMinPoolSize());
        cpds.setMaxPoolSize(conf.getMaxPoolSize());
        cpds.setIdleConnectionTestPeriod(conf.getIdleConnectionTestPeriod());
        cpds.setTestConnectionOnCheckout(true);
        cpds.setConnectionCustomizerClassName("com.bolyartech.forge.server.db.C3p0ConnectionCustomizer");

        return new C3p0DbPool(cpds);
    }
}
