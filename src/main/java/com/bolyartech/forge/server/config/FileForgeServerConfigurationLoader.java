package com.bolyartech.forge.server.config;

import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import java.io.*;
import java.text.MessageFormat;
import java.util.Properties;


/**
 * Implementation of {@link ForgeServerConfigurationLoader} that loads from property file
 */
public class FileForgeServerConfigurationLoader implements ForgeServerConfigurationLoader {
    private static final String FILENAME = "forge.conf";
    private static final String PROP_SERVER_LOG_NAME = "server_log_name";
    private static final String PROP_STATIC_FILES_DIR = "static_files_dir";
    private final org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());

    private final String configDir;


    /**
     * Creates new FileForgeServerConfigurationLoader
     *
     * @param configDir Path to the directory that contains the configuration files
     */
    public FileForgeServerConfigurationLoader(@Nonnull String configDir) {
        this.configDir = configDir;
    }


    @Override
    public ForgeServerConfiguration load() throws ForgeConfigurationException {
        File confFile = new File(configDir, FILENAME);
        if (confFile.exists()) {

            Properties prop = new Properties();
            try {
                InputStream is = new BufferedInputStream(new FileInputStream(confFile));
                prop.load(is);
                is.close();
            } catch (IOException e) {
                logger.error("Cannot load config file", e);
                throw new ForgeConfigurationException(e);
            }

            try {
                return new ForgeServerConfigurationImpl(prop.getProperty(PROP_SERVER_LOG_NAME),
                        prop.getProperty(PROP_STATIC_FILES_DIR));

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
