package com.bolyartech.forge.server.module;

import com.bolyartech.forge.server.HttpMethod;
import com.bolyartech.forge.server.route.Route;


/**
 * Register for HTTP modules
 */
public interface HttpModuleRegister {
    /**
     * Registers a module
     *
     * @param mod module to be registered
     */
    void registerModule(HttpModule mod);

    /**
     * Checks if module is registered
     *
     * @param mod module
     * @return true if module is registered, false otherwise
     */
    boolean isModuleRegistered(HttpModule mod);

    /**
     * Matches {@link HttpMethod} and path to a route in the registered modules
     *
     * @param method HTTP method like GET, POST, etc.
     * @param path   Path to be matched
     * @return Route with matched method and path or null if no route is matched
     */
    Route match(HttpMethod method, String path);
}
