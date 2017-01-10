package com.bolyartech.forge.server.db;

import com.bolyartech.forge.server.config.ForgeConfigurationException;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


/**
 * Implementation of {@link DbConfigurationLoader} that loads from property file
 */
public class FileDbConfigurationLoader implements DbConfigurationLoader {
    private static final String FILENAME = "conf/db.conf";
    private static final String PROP_DB_DSN = "db_dsn";
    private static final String PROP_DB_USERNAME = "db_username";
    private static final String PROP_DB_PASSWORD = "db_password";
    private static final String PROP_C3P0_MAX_STATEMENTS = "c3p0_max_statements";
    private static final String PROP_C3P0_INITIAL_POOL_SIZE = "c3p0_initial_pool_size";
    private static final String PROP_C3P0_MIN_POOL_SIZE = "c3p0_min_pool_size";
    private static final String PROP_C3P0_MAX_POOL_SIZE = "c3p0_max_pool_size";
    private static final String PROP_C3P0_IDLE_CONNECTION_TEST_PERIOD = "c3p0_idle_connection_test_period";
    private static final String PROP_C3P0_TEST_CONNECTION_ON_CHECKIN = "c3p0_test_connection_on_checkin";
    private static final String PROP_C3P0_TEST_CONNECTION_ON_CHECKOUT = "c3p0_test_connection_on_checkout";
    private final org.slf4j.Logger mLogger = LoggerFactory.getLogger(this.getClass());


    private final ClassLoader mClassLoader;


    /**
     * Creates new FileDbConfigurationLoader
     *
     * @param classLoader Class loader to be used to find resources
     */
    public FileDbConfigurationLoader(ClassLoader classLoader) {
        mClassLoader = classLoader;
    }


    /**
     * Creates new FileDbConfigurationLoader
     */
    public FileDbConfigurationLoader() {
        mClassLoader = this.getClass().getClassLoader();
    }


    @Override
    public DbConfiguration load() throws ForgeConfigurationException {
        InputStream is = mClassLoader.getResourceAsStream(FILENAME);
        if (is != null) {
            Properties prop = new Properties();
            try {
                prop.load(is);

            } catch (IOException e) {
                mLogger.error("Cannot load config file");
                throw new ForgeConfigurationException(e);
            }

            try {
                return new DbConfigurationImpl(prop.getProperty(PROP_DB_DSN),
                        prop.getProperty(PROP_DB_USERNAME),
                        prop.getProperty(PROP_DB_PASSWORD),
                        Integer.parseInt(prop.getProperty(PROP_C3P0_MAX_STATEMENTS)),
                        Integer.parseInt(prop.getProperty(PROP_C3P0_INITIAL_POOL_SIZE)),
                        Integer.parseInt(prop.getProperty(PROP_C3P0_MIN_POOL_SIZE)),
                        Integer.parseInt(prop.getProperty(PROP_C3P0_MAX_POOL_SIZE)),
                        Integer.parseInt(prop.getProperty(PROP_C3P0_IDLE_CONNECTION_TEST_PERIOD)),
                        Boolean.valueOf(prop.getProperty(PROP_C3P0_TEST_CONNECTION_ON_CHECKIN)),
                        Boolean.valueOf(prop.getProperty(PROP_C3P0_TEST_CONNECTION_ON_CHECKOUT))
                );
            } catch (Exception e) {
                mLogger.error("Error populating configuration", e);
                throw new ForgeConfigurationException(e);
            }
        } else {
            mLogger.error("Problem  finding/loading configuration file " + FILENAME);
            throw new ForgeConfigurationException();
        }
    }
}
