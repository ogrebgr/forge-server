package com.bolyartech.forge.server.handler;

import com.bolyartech.forge.server.misc.TemplateEngine;
import com.bolyartech.forge.server.route.RequestContext;

import java.sql.Connection;


public interface DbWebPageInterface extends RouteHandler {
    abstract public String produceHtml(RequestContext ctx, TemplateEngine tple, Connection dbc);
}
