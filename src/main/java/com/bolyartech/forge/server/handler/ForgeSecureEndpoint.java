package com.bolyartech.forge.server.handler;

import com.bolyartech.forge.server.response.ResponseException;
import com.bolyartech.forge.server.response.forge.ForgeResponse;
import com.bolyartech.forge.server.response.forge.RequiresHttpsResponse;
import com.bolyartech.forge.server.route.RequestContext;


/**
 * Endpoint that requires request to be using HTTPS
 * @deprecated If you need to prevent http access just disable it in jetty.conf (http_port=0)
 */
abstract public class ForgeSecureEndpoint extends ForgeEndpoint {
    /**
     * Handles request which is guaranteed to be on HTTPS
     *
     * @param ctx Response context
     * @return ForgeResponse
     * @throws ResponseException if there is a problem handling the request
     */
    abstract public ForgeResponse handleForgeSecure(RequestContext ctx) throws ResponseException;


    @Override
    public ForgeResponse handleForge(RequestContext ctx) {
        if (ctx.getScheme().equals("https")) {
            return handleForgeSecure(ctx);
        } else {
            return new RequiresHttpsResponse();
        }
    }
}
