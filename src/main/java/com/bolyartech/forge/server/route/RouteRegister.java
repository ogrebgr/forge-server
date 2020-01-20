package com.bolyartech.forge.server.route;

import com.bolyartech.forge.server.HttpMethod;
import com.google.common.base.Strings;

import javax.annotation.Nonnull;


/**
 * Register for routes
 */
public interface RouteRegister {
    /**
     * Registers a route
     *
     * @param moduleSystemName Module system name
     * @param route            Route
     */
    void register(String moduleSystemName, Route route);


    /**
     * Checks if a route is registered
     *
     * @param route Route
     * @return true if route is registered, false otherwise
     */
    boolean isRegistered(@Nonnull Route route);

    /**
     * Returns route registration
     *
     * @param route Route object
     * @return Registration of the route
     */
    Registration getRegistration(@Nonnull Route route);

    /**
     * Matches Route against HTTP method and URL path
     *
     * @param method HTTP method
     * @param path   URL Path
     * @return matched route or null if no route is matched
     */
    Route match(@Nonnull HttpMethod method, @Nonnull String path);


    class Registration {
        public final String moduleName;
        public final Route mRoute;


        Registration(@Nonnull String moduleName, @Nonnull Route route) {
            if (Strings.isNullOrEmpty(moduleName)) {
                throw new IllegalArgumentException("moduleName is empty");
            }

            if (route == null) {
                throw new NullPointerException("mRoute is null");
            }

            this.moduleName = moduleName;
            this.mRoute = route;
        }
    }
}
