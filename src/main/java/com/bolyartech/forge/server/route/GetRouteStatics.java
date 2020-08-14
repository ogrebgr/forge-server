package com.bolyartech.forge.server.route;

import com.bolyartech.forge.server.HttpMethod;
import com.bolyartech.forge.server.handler.StaticFileHandler;

import javax.annotation.Nonnull;


/**
 * Get route for static files like html, images, css, js
 */
public class GetRouteStatics extends RouteImpl {
    /**
     * Creates new GetRoute
     *
     * @param path         URL path of the route
     * @param routeHandler Route handler
     */
    public GetRouteStatics(@Nonnull String path, @Nonnull StaticFileHandler routeHandler) {
        super(HttpMethod.GET, path, routeHandler, true);
    }
}
