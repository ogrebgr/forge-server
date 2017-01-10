package com.bolyartech.forge.server.db;

import com.bolyartech.forge.server.config.ForgeConfigurationException;


/**
 * Defines interface for DB configuration loader
 */
public interface DbConfigurationLoader {
    /**
     * Loads DB configuration
     *
     * @return DB configuration
     * @throws ForgeConfigurationException if there is a problem loading configuration file
     */
    DbConfiguration load() throws ForgeConfigurationException;
}
