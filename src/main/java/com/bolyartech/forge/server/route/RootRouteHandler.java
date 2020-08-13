package com.bolyartech.forge.server.route;

import com.bolyartech.forge.server.handler.RouteHandler;
import com.bolyartech.forge.server.handler.StaticFileHandler;
import com.bolyartech.forge.server.handler.StaticResourceNotFoundException;
import com.bolyartech.forge.server.response.Response;
import com.bolyartech.forge.server.response.ResponseException;
import org.checkerframework.checker.nullness.qual.NonNull;


/**
 * Route handler for "root" routes.
 * It first checks if a static file exists (using staticsRouteHandler) and only if not - proceeds with the "normal" routeHandler
 */
public class RootRouteHandler implements RouteHandler {
    private final StaticFileHandler staticsRouteHandler;
    private final RouteHandler routeHandler;


    /**
     * @param staticsRouteHandler StaticFileHandler for static files like images, css, html
     * @param routeHandler        handler for "normal" dynamic pages and endpoints
     */
    public RootRouteHandler(@NonNull StaticFileHandler staticsRouteHandler, @NonNull RouteHandler routeHandler) {
        this.staticsRouteHandler = staticsRouteHandler;
        this.routeHandler = routeHandler;
    }


    @Override
    public Response handle(@NonNull RequestContext ctx) throws ResponseException, StaticResourceNotFoundException {
        try {
            return staticsRouteHandler.handle(ctx);
        } catch (StaticResourceNotFoundException e) {
            return routeHandler.handle(ctx);
        }
    }
}
