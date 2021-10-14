package com.bolyartech.forge.server.route;

import com.bolyartech.forge.server.HttpMethod;
import com.bolyartech.forge.server.handler.RouteHandler;

import javax.annotation.Nonnull;


/**
 * Route that uses HTTP GET method
 */
public class GetRoute extends RouteImpl {
    /**
     * Creates new GetRoute
     *
     * @param path         URL path of the route
     * @param routeHandler Route handler
     */
    public GetRoute(@Nonnull String path, @Nonnull RouteHandler routeHandler, boolean isSupportingPathInfo) {
        super(HttpMethod.GET, path, routeHandler, isSupportingPathInfo);
    }


    public GetRoute(@Nonnull String path, @Nonnull RouteHandler routeHandler) {
        this(path, routeHandler, false);
    }
}
