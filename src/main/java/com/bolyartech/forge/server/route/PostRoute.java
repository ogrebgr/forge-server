package com.bolyartech.forge.server.route;

import com.bolyartech.forge.server.HttpMethod;
import com.bolyartech.forge.server.handler.RouteHandler;

import javax.annotation.Nonnull;


/**
 * Route that uses HTTP POST method
 */
public class PostRoute extends RouteImpl {
    /**
     * Creates new PostRoute
     *
     * @param path         URL path of the route
     * @param routeHandler Route handler
     */
    public PostRoute(@Nonnull String path, @Nonnull RouteHandler routeHandler, boolean isSupportingPathInfo) {
        super(HttpMethod.POST, path, routeHandler, isSupportingPathInfo);
    }


    public PostRoute(@Nonnull String path, @Nonnull RouteHandler routeHandler) {
        this(path, routeHandler, false);
    }
}
