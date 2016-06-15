package com.bolyartech.forge.server.module;

public interface ForgeModule {
    void registerEndpoints();
    String getSystemName();
    String getShortDescription();
    int getVersionCode();
    String getVersionName();
}
