package com.bolyartech.forge.server.route;

import com.bolyartech.forge.server.HttpMethod;
import com.bolyartech.forge.server.handler.RouteHandler;


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
    public GetRoute(String path, RouteHandler routeHandler) {
        super(HttpMethod.GET, path, routeHandler);
    }
}
