package com.bolyartech.forge.server.config;


public class ForgeServerConfigurationImpl implements ForgeServerConfiguration {
    private final String mServerLogName;


    public ForgeServerConfigurationImpl(String serverLogName) {
        mServerLogName = serverLogName;
    }


    @Override
    public String getServerLogName() {
        return mServerLogName;
    }
}
