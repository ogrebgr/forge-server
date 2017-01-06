package com.bolyartech.forge.server.route;

import com.bolyartech.forge.server.HttpMethod;
import com.google.common.base.Strings;


public interface RouteRegister {
    void register(String moduleName, Route ep);

    boolean isRegistered(Route ep);

    Registration getRegistration(Route ep);

    Route match(HttpMethod method, String path);


    class Registration {
        public final String moduleName;
        public final Route mRoute;


        public Registration(String moduleName, Route route) {
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
