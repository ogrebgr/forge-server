package com.bolyartech.forge.server.route;

import com.bolyartech.forge.server.HttpMethod;
import com.bolyartech.forge.server.handler.RouteHandler;


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
    public PostRoute(String path, RouteHandler routeHandler) {
        super(HttpMethod.POST, path, routeHandler);
    }
}
