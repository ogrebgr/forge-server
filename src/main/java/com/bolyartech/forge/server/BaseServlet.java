package com.bolyartech.forge.server;

import com.bolyartech.forge.server.handler.RouteHandler;
import com.bolyartech.forge.server.route.Route;

import javax.annotation.Nonnull;


public interface BaseServlet {
    void addRoute(@Nonnull Route route);

    void addRoute(@Nonnull HttpMethod httpMethod, @Nonnull String path, @Nonnull RouteHandler routeHandler);
}
