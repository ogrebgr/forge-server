package com.bolyartech.forge.server.db;

import com.bolyartech.forge.server.config.ForgeConfigurationException;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import java.io.*;
import java.text.MessageFormat;
import java.util.Properties;


/**
 * Implementation of {@link DbConfigurationLoader} that loads from property file
 */
public class FileDbConfigurationLoader implements DbConfigurationLoader {
    private static final String FILENAME = "db.conf";
    private static final String PROP_DB_DSN = "db_dsn";
    private static final String PROP_DB_USERNAME = "db_username";
    private static final String PROP_DB_PASSWORD = "db_password";
    private static final String PROP_C3P0_MAX_STATEMENTS = "c3p0_max_statements";
    private static final String PROP_C3P0_INITIAL_POOL_SIZE = "c3p0_initial_pool_size";
    private static final String PROP_C3P0_MIN_POOL_SIZE = "c3p0_min_pool_size";
    private static final String PROP_C3P0_MAX_POOL_SIZE = "c3p0_max_pool_size";
    private static final String PROP_C3P0_IDLE_CONNECTION_TEST_PERIOD = "c3p0_idle_connection_test_period";
    private static final String PROP_C3P0_TEST_CONNECTION_ON_CHECK_IN = "c3p0_test_connection_on_check_in";
    private static final String PROP_C3P0_TEST_CONNECTION_ON_CHECKOUT = "c3p0_test_connection_on_checkout";
    private final org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());
    private final String configDir;


    /**
     * Creates new FileDbConfigurationLoader
     *
     * @param configDir Configuration directory
     */
    public FileDbConfigurationLoader(@Nonnull String configDir) {
        this.configDir = configDir;
    }


    @Override
    public DbConfiguration load() throws ForgeConfigurationException {
        File confFile = new File(configDir, FILENAME);
        if (confFile.exists()) {

            Properties prop = new Properties();
            try {
                InputStream is = new BufferedInputStream(new FileInputStream(confFile));
                prop.load(is);
                is.close();
            } catch (IOException e) {
                logger.error("Cannot load config file");
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
                        Boolean.parseBoolean(prop.getProperty(PROP_C3P0_TEST_CONNECTION_ON_CHECK_IN)),
                        Boolean.parseBoolean(prop.getProperty(PROP_C3P0_TEST_CONNECTION_ON_CHECKOUT))
                );
            } catch (Exception e) {
                logger.error("Error populating configuration", e);
                throw new ForgeConfigurationException(e);
            }
        } else {
            logger.error("Cannot find configuration file: {}", confFile.getAbsolutePath());
            throw new IllegalStateException(MessageFormat.format("Cannot find configuration file: {0}",
                    confFile.getAbsolutePath()));
        }
    }
}
