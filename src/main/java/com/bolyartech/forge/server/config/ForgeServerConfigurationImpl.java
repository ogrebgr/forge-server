package com.bolyartech.forge.server.config;


import javax.annotation.Nonnull;
import java.io.File;


/**
 * Implementation of {@link ForgeServerConfiguration}
 */
public class ForgeServerConfigurationImpl implements ForgeServerConfiguration {
    private final String serverLogName;
    private final String staticFilesDir;
    private final boolean isPathInfoEnabled;
    private final int maxSlashesInPathInfo;


    /**
     * Creates new ForgeServerConfigurationImpl
     *
     * @param serverLogName        Prefix for log files for this server
     * @param staticFilesDir       Path to the directory for static files like js, css, images
     * @param isPathInfoEnabled    Enable or disable PATH_INFO functionality. This directive controls whether requests that contain trailing pathname information that follows an actual filename (or non-existent file in an existing directory) will be accepted or rejected. The trailing pathname information can be made available to scripts in the ctx.getPathInfoParameters(). @see https://httpd.apache.org/docs/2.4/mod/core.html#acceptpathinfo
     * @param maxSlashesInPathInfo Limits number of slashes in path. Used as prevention against DDOS attacks. Set to 0 for no limit. If there are more slashes in given path - route matching will not work
     */
    public ForgeServerConfigurationImpl(@Nonnull String serverLogName,
                                        @Nonnull String staticFilesDir,
                                        boolean isPathInfoEnabled,
                                        int maxSlashesInPathInfo) {

        this.serverLogName = serverLogName;
        this.isPathInfoEnabled = isPathInfoEnabled;
        this.maxSlashesInPathInfo = maxSlashesInPathInfo;
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


    @Override
    public boolean isPathInfoEnabled() {
        return isPathInfoEnabled;
    }


    @Override
    public int getMaxSlashesInPathInfo() {
        return maxSlashesInPathInfo;
    }
}
