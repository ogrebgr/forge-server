package com.bolyartech.forge.server.handler;

import com.bolyartech.forge.server.response.ResponseException;
import com.bolyartech.forge.server.response.forge.ForgeResponse;
import com.bolyartech.forge.server.route.RequestContext;


/**
 * Specialized handler that produces {@link ForgeResponse} as response
 */
abstract public class ForgeEndpoint implements RouteHandler {
    /**
     * Handles HTTP request wrapped in {@link RequestContext} object and produces {@link ForgeResponse}
     *
     * @param ctx Request context
     * @return Forge response
     * @throws ResponseException if there is problem handling the request
     */
    abstract public ForgeResponse handleForge(RequestContext ctx) throws ResponseException;


    @Override
    public ForgeResponse handle(RequestContext ctx) {
        return handleForge(ctx);
    }
}
