package com.bolyartech.forge.server.config;


/**
 * Implementation of {@link ForgeServerConfiguration}
 */
public class ForgeServerConfigurationImpl implements ForgeServerConfiguration {
    private final String mServerLogName;


    /**
     * Creates new ForgeServerConfigurationImpl
     *
     * @param serverLogName Prefix to be used for the log file
     */
    public ForgeServerConfigurationImpl(String serverLogName) {
        mServerLogName = serverLogName;
    }


    @Override
    public String getServerLogName() {
        return mServerLogName;
    }
}
