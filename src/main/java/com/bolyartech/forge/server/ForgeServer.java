package com.bolyartech.forge.server;


import com.bolyartech.forge.server.config.ServerConfiguration;

/**
 * ForgeServer base interface
 */
public interface ForgeServer {
    /**
     * Initializes the server
     */
    void start();

    /**
     * Stops the server
     */
    void stop();

    /**
     * @return Server configuration
     */
    ServerConfiguration getServerConfiguration();
}
