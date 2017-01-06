package com.bolyartech.forge.server.handler;

import com.bolyartech.forge.server.response.ResponseException;
import com.bolyartech.forge.server.response.forge.ForgeResponse;
import com.bolyartech.forge.server.route.RequestContext;
import com.bolyartech.forge.server.session.Session;


public interface ForgeHandlerInterface {
    ForgeResponse handleForge(RequestContext ctx, Session session) throws ResponseException;
}
