package com.bolyartech.forge.server.handler;

import com.bolyartech.forge.server.response.Response;
import com.bolyartech.forge.server.response.ResponseException;
import com.bolyartech.forge.server.route.RequestContext;
import com.bolyartech.forge.server.session.Session;


public interface Handler {
    Response handle(RequestContext ctx, Session session) throws ResponseException;
}
