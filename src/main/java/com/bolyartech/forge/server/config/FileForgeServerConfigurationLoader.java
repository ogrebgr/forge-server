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
    private static final String PROP_PATH_INFO_ENABLED = "path_info_enabled";
    private static final String PROP_MAX_SLASHES_IN_PATH_INFO = "max_slashes_in_path_info";
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
                String logName = prop.getProperty(PROP_SERVER_LOG_NAME);
                if (logName == null) {
                    logger.error("Missing {} in forge.conf", PROP_SERVER_LOG_NAME);
                }

                String staticDir = prop.getProperty(PROP_STATIC_FILES_DIR);
                if (staticDir == null) {
                    logger.error("Missing {} in forge.conf", PROP_STATIC_FILES_DIR);
                }

                if (logName == null || staticDir == null) {
                    throw new ForgeConfigurationException("Missing properties");
                }

                String isPathInfoEnabledRaw = prop.getProperty(PROP_PATH_INFO_ENABLED);
                boolean isPathInfoEnabled = ForgeServerConfiguration.DEFAULT_IS_PATH_INFO_ENABLED;
                if (isPathInfoEnabledRaw != null) {
                    String tmp = isPathInfoEnabledRaw.trim().toLowerCase();
                    isPathInfoEnabled = tmp.equals("true") || tmp.equals("1");
                }

                String maxSlashesRaw = prop.getProperty(PROP_MAX_SLASHES_IN_PATH_INFO);

                int maxSlashes = ForgeServerConfiguration.DEFAULT_MAX_SLASHES_IN_PATH_INFO;
                try {
                    maxSlashes = Integer.parseInt(maxSlashesRaw);
                } catch (NumberFormatException e) {
                    logger.error("Invalid value for {}. Must be integer", PROP_MAX_SLASHES_IN_PATH_INFO);
                }

                return new ForgeServerConfigurationImpl(logName, staticDir, isPathInfoEnabled, maxSlashes);
            } catch (Exception e) {
                logger.error("Error populating configuration");
                throw new ForgeConfigurationException(e);
            }
        } else {
            logger.error("Cannot find configuration file: {}", confFile.getAbsolutePath());
            throw new IllegalStateException(MessageFormat.format("Cannot find configuration file: {0}",
                    confFile.getAbsolutePath()));
        }
    }
}
