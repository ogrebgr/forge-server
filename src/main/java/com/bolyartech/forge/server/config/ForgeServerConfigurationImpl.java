package com.bolyartech.forge.server.config;


import java.io.File;


/**
 * Implementation of {@link ForgeServerConfiguration}
 */
public class ForgeServerConfigurationImpl implements ForgeServerConfiguration {
    private final String serverLogName;
    private final String staticFilesDir;


    /**
     * Creates new ForgeServerConfigurationImpl
     *
     * @param serverLogName  Prefix for log files for this server
     * @param staticFilesDir Path to the directory for static files like js, css, images
     */
    public ForgeServerConfigurationImpl(String serverLogName, String staticFilesDir) {
        this.serverLogName = serverLogName;
        if (!staticFilesDir.endsWith(File.separator)) {
            staticFilesDir += File.separator;
        }
        this.staticFilesDir = staticFilesDir;
    }


    @Override
    public String getServerLogName() {
        return serverLogName;
    }


    @Override
    public String getStaticFilesDir() {
        return staticFilesDir;
    }
}
