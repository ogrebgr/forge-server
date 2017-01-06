package com.bolyartech.forge.server.module;

import com.bolyartech.forge.server.route.Route;

import java.util.List;


public interface HttpModule {
    List<Route> createRoutes();

    String getSystemName();

    String getShortDescription();

    int getVersionCode();

    String getVersionName();
}
