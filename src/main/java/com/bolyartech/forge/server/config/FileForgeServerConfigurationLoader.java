package com.bolyartech.forge.server.config;

import org.slf4j.LoggerFactory;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.Properties;


/**
 * Implementation of {@link ForgeServerConfigurationLoader} that loads from property file
 */
public class FileForgeServerConfigurationLoader implements ForgeServerConfigurationLoader {
    private static final String FILENAME = "forge.conf";
    private static final String PROP_SERVER_LOG_NAME = "server_log_name";
    private static final String PROP_STATIC_FILES_DIR = "static_files_dir";
    private final org.slf4j.Logger mLogger = LoggerFactory.getLogger(this.getClass());

    private final String mConfigDir;


    /**
     * Creates new FileForgeServerConfigurationLoader
     *
     * @param configDir Path to the directory that contains the configuration files
     */
    public FileForgeServerConfigurationLoader(String configDir) {
        mConfigDir = configDir;
    }


    @Override
    public ForgeServerConfiguration load() throws ForgeConfigurationException {
        File confFile = new File(mConfigDir, FILENAME);
        if (confFile.exists()) {

            Properties prop = new Properties();
            try {
                prop.load(new BufferedInputStream(new FileInputStream(confFile)));
            } catch (IOException e) {
                mLogger.error("Cannot load config file", e);
                throw new ForgeConfigurationException(e);
            }

            try {
                return new ForgeServerConfigurationImpl(prop.getProperty(PROP_SERVER_LOG_NAME),
                        prop.getProperty(PROP_STATIC_FILES_DIR));

            } catch (Exception e) {
                mLogger.error("Error populating configuration", e);
                throw new ForgeConfigurationException(e);
            }
        } else {
            mLogger.error("Cannot find configuration file: {}", confFile.getAbsolutePath());
            throw new IllegalStateException(MessageFormat.format("Cannot find configuration file: {0}",
                    confFile.getAbsolutePath()));
        }
    }
}
