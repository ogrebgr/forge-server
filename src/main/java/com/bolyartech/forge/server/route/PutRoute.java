package com.bolyartech.forge.server.route;

import com.bolyartech.forge.server.HttpMethod;
import com.bolyartech.forge.server.handler.RouteHandler;

import javax.annotation.Nonnull;

/**
 * Route that uses HTTP PUT method
 */
public class PutRoute extends RouteImpl {
    public PutRoute(@Nonnull String path, @Nonnull RouteHandler routeHandler) {
        super(HttpMethod.PUT, path, routeHandler);
    }
}
