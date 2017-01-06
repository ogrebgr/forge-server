package com.bolyartech.forge.server.db;

import com.bolyartech.forge.server.config.ForgeConfigurationException;


public interface DbConfigurationLoader {
    DbConfiguration load(ClassLoader cl) throws ForgeConfigurationException;
}
