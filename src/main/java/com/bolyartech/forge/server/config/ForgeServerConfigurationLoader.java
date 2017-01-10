package com.bolyartech.forge.server.config;

/**
 * Defines interface for forge server configuration loader
 */
public interface ForgeServerConfigurationLoader {
    /**
     * Loads forge server configuration
     *
     * @return Loaded configuration
     * @throws ForgeConfigurationException if there is a problem with loading the configuration file or the data
     *                                     in the configuration file
     */
    ForgeServerConfiguration load() throws ForgeConfigurationException;
}
