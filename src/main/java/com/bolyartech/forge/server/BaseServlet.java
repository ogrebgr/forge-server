package com.bolyartech.forge.server;

import com.bolyartech.forge.server.handler.RouteHandler;
import com.bolyartech.forge.server.route.Route;


public interface BaseServlet {
    void addRoute(Route route);

    void addRoute(HttpMethod httpMethod, String path, RouteHandler routeHandler);
}
