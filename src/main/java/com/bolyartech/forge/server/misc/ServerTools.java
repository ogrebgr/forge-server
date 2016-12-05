package com.bolyartech.forge.server.misc;

import com.bolyartech.forge.server.config.DbConfiguration;
import com.bolyartech.forge.server.config.DbConfigurationImpl;
import com.bolyartech.forge.server.db.C3p0DbPool;
import com.bolyartech.forge.server.db.DbPool;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ServerTools {
    private static final org.slf4j.Logger mLogger = LoggerFactory.getLogger("ServerTools");


    /**
     * Non-instantiable utility class
     */
    private ServerTools() {
        throw new AssertionError();
    }


    public static DbConfiguration loadDbConf(File configDir, String filepath) {
        return loadDbConf(new File(configDir, filepath));
    }


    public static DbConfiguration loadDbConf(File dbConfFile) {
        try {
            try (FileInputStream is = new FileInputStream(dbConfFile)) {
                Properties prop = new Properties();
                prop.load(is);
                mLogger.info("Found DB config for {}", prop.getProperty("db_dsn"));
                return new DbConfigurationImpl(prop.getProperty("db_dsn"),
                        prop.getProperty("db_username"),
                        prop.getProperty("db_password"),
                        Integer.parseInt(prop.getProperty("c3p0_max_statements")),
                        Integer.parseInt(prop.getProperty("c3p0_initial_pool_size")),
                        Integer.parseInt(prop.getProperty("c3p0_min_pool_size")),
                        Integer.parseInt(prop.getProperty("c3p0_max_pool_size")),
                        Integer.parseInt(prop.getProperty("c3p0_idle_connection_test_period")),
                        Boolean.valueOf(prop.getProperty("c3p0_test_connection_on_checkin")),
                        Boolean.valueOf(prop.getProperty("c3p0_test_connection_on_checkout"))
                );
            }
        } catch (IOException e) {
            mLogger.error("Problem loading configuration from " + dbConfFile.getAbsolutePath());
            throw new RuntimeException("Unable to start.");
        }
    }



    public static DbPool createComboPooledDataSource(DbConfiguration conf) {
        Properties p = new Properties(System.getProperties());
        p.put("com.mchange.v2.log.MLog", "com.mchange.v2.log.FallbackMLog");
        p.put("com.mchange.v2.log.FallbackMLog.DEFAULT_CUTOFF_LEVEL", "OFF"); // or any other
        System.setProperties(p);

        ComboPooledDataSource cpds = new ComboPooledDataSource();
        cpds.setJdbcUrl(conf.getDbDsn());
        cpds.setUser(conf.getDbUsername());
        cpds.setPassword(conf.getDbPassword());
        cpds.setMaxStatements(conf.getMaxStatements());
        cpds.setInitialPoolSize(conf.getInitalPoolSize());
        cpds.setMinPoolSize(conf.getMinPoolSize());
        cpds.setMaxPoolSize(conf.getMaxPoolSize());
        cpds.setIdleConnectionTestPeriod(conf.getIdleConnectionTestPeriod());
        cpds.setTestConnectionOnCheckout(true);
        cpds.setConnectionCustomizerClassName("com.bolyartech.forge.server.db.C3p0ConnectionCustomizer");

        return new C3p0DbPool(cpds);
    }
}
