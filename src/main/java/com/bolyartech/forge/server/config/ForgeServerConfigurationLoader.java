package com.bolyartech.forge.server.config;

public interface ForgeServerConfigurationLoader {
    ForgeServerConfiguration load(ClassLoader cl) throws ForgeConfigurationException;
}
