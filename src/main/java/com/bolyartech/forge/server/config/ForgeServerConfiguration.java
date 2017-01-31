package com.bolyartech.forge.server.config;


/**
 * Defines interface for server configuration
 */
public interface ForgeServerConfiguration {
    /**
     * Returns server log name
     * Server log name will be used as prefix to log files. For example for 'myserver' log files will \
     * look like: myserver.2016-12-16.log
     *
     * @return server's log name
     */
    String getServerLogName();
}