package com.bolyartech.forge.server.config;


import java.io.File;


/**
 * Implementation of {@link ForgeServerConfiguration}
 */
public class ForgeServerConfigurationImpl implements ForgeServerConfiguration {
    private final String mServerLogName;
    private final String mStaticFilesDir;


    /**
     * Creates new ForgeServerConfigurationImpl
     *
     * @param serverLogName  Prefix for log files for this server
     * @param staticFilesDir Path to the directory for static files like js, css, images
     */
    public ForgeServerConfigurationImpl(String serverLogName, String staticFilesDir) {
        mServerLogName = serverLogName;
        if (!staticFilesDir.endsWith(File.separator)) {
            staticFilesDir += File.separator;
        }
        mStaticFilesDir = staticFilesDir;
    }


    @Override
    public String getServerLogName() {
        return mServerLogName;
    }


    @Override
    public String getStaticFilesDir() {
        return mStaticFilesDir;
    }
}
