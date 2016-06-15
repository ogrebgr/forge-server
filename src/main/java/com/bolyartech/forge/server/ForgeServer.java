package com.bolyartech.forge.server;


import com.bolyartech.forge.server.config.ServerConfiguration;

/**
 * ForgeServer base interface
 */
public interface ForgeServer {
    /**
     * Initializes the server
     */
    void init();

    /**
     * @return Server configuration
     */
    ServerConfiguration getServerConfiguration();
}
