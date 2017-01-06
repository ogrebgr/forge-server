package com.bolyartech.forge.server.module;

import com.bolyartech.forge.server.HttpMethod;
import com.bolyartech.forge.server.route.Route;


public interface ModuleRegister {
    void registerModule(HttpModule mod);

    boolean isModuleRegistered(HttpModule mod);

    Route match(HttpMethod method, String path);
}
