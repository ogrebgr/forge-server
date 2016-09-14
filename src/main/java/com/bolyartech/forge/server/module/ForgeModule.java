package com.bolyartech.forge.server.module;

public interface ForgeModule {
    void registerEndpoints();
    String getSystemName();
    @SuppressWarnings("SameReturnValue")
    String getShortDescription();
    int getVersionCode();
    String getVersionName();
}
