package com.bolyartech.forge.server.module;

import com.bolyartech.forge.server.route.Route;

import java.util.List;


/**
 * Module that contains web pages, endpoints or other route handlers
 * Modules are the main building block of a site/system
 */
public interface HttpModule {
    /**
     * In this method modules should create their routes and return them
     *
     * @return routes of the module
     */
    List<Route> createRoutes();

    /**
     * Unique module name
     * <p>
     * Module names must be lowercase, contain only letter, numbers, dot or hyphen
     *
     * @return system name of the module
     */
    String getSystemName();

    /**
     * Returns short description of the module
     *
     * @return Short description of the module
     */
    String getShortDescription();

    /**
     * Version code of the module
     * <p>
     * Version codes start from 1 and are changed in each release. There must not be two releases with the same
     * version code. Version codes are usually used not by humans but by module management systems.
     *
     * @return version code
     */
    int getVersionCode();

    /**
     * Version name is version information which is meant to be used by humans like v3.2.1alpha
     *
     * @return version name
     */
    String getVersionName();
}
