package com.bolyartech.forge.server.config;

public interface ServerConfiguration {
    String getServerNameSuffix();
    String getLogBackConfigFile();
    int getHttpPort();
    String getKeyStorePath();
    String getKeyStorePassword();
    int getSessionTimeout();
}
