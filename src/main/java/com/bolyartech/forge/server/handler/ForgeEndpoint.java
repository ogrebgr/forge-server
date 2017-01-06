package com.bolyartech.forge.server.handler;

import com.bolyartech.forge.server.response.Response;
import com.bolyartech.forge.server.response.ResponseException;
import com.bolyartech.forge.server.route.RequestContext;
import com.bolyartech.forge.server.session.Session;


abstract public class ForgeEndpoint implements Handler, ForgeHandlerInterface {
    @Override
    public Response handle(RequestContext ctx, Session session) throws ResponseException {
        return handleForge(ctx, session);
    }
}
