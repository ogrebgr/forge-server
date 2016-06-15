package com.bolyartech.forge.server.config;

public class ServerConfigurationImpl implements ServerConfiguration {
    private final String mServerNameSuffix;
    private final String mLogBackConfigFile;

    private final int mHttpPort;
    private final String mKeyStorePath;
    private final String mKeyStorePassword;
    private final int mSessionTimeout;


    public ServerConfigurationImpl(String serverNameSuffix,
                                   String logBackConfigFile,
                                   int httpPort,
                                   int sessionTimeout,
                                   String keyStorePath,
                                   String keeStorePassword) {

        mServerNameSuffix = serverNameSuffix;
        mLogBackConfigFile = logBackConfigFile;
        mHttpPort = httpPort;
        mKeyStorePath = keyStorePath;
        mKeyStorePassword = keeStorePassword;
        mSessionTimeout = sessionTimeout;
    }


    @Override
    public String getServerNameSuffix() {
        return mServerNameSuffix;
    }


    @Override
    public String getLogBackConfigFile() {
        return mLogBackConfigFile;
    }


    @Override
    public int getHttpPort() {
        return mHttpPort;
    }


    public String getKeyStorePath() {
        return mKeyStorePath;
    }


    public String getKeyStorePassword() {
        return mKeyStorePassword;
    }


    @Override
    public int getSessionTimeout() {
        return mSessionTimeout;
    }
}
