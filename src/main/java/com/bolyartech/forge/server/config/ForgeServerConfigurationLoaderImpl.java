package com.bolyartech.forge.server.config;

import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class ForgeServerConfigurationLoaderImpl implements ForgeServerConfigurationLoader {
    private static final String FILENAME = "conf/forge.conf";
    private static final String PROP_SERVER_LOG_NAME = "server_log_name";
    private final org.slf4j.Logger mLogger = LoggerFactory.getLogger(this.getClass());


    @Override
    public ForgeServerConfiguration load(ClassLoader cl) throws ForgeConfigurationException {
        InputStream is = cl.getResourceAsStream(FILENAME);
        if (is != null) {
            Properties prop = new Properties();
            try {
                prop.load(is);

            } catch (IOException e) {
                mLogger.error("Cannot load config file");
                throw new ForgeConfigurationException(e);
            }

            try {
                return new ForgeServerConfigurationImpl(prop.getProperty(PROP_SERVER_LOG_NAME));
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
