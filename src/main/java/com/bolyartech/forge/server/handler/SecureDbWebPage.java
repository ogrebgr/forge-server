package com.bolyartech.forge.server.handler;

import com.bolyartech.forge.server.db.DbPool;
import com.bolyartech.forge.server.misc.TemplateEngine;
import com.bolyartech.forge.server.misc.TemplateEngineFactory;
import com.bolyartech.forge.server.response.Response;
import com.bolyartech.forge.server.response.forge.RequiresHttpsResponse;
import com.bolyartech.forge.server.route.RequestContext;

import java.sql.Connection;


abstract public class SecureDbWebPage extends DbWebPage {

    public SecureDbWebPage(TemplateEngineFactory templateEngineFactory, DbPool dbPool) {
        this(templateEngineFactory, false, dbPool);
    }


    public SecureDbWebPage(TemplateEngineFactory templateEngineFactory, boolean enableGzipSupport, DbPool dbPool) {
        super(templateEngineFactory, enableGzipSupport, dbPool);
    }


    abstract public String produceHtml(RequestContext ctx, TemplateEngine tple, Connection dbc);


    @Override
    public Response handle(RequestContext ctx) {
        if (ctx.getScheme().equals("https")) {
            return super.handle(ctx);
        } else {
            return new RequiresHttpsResponse();
        }
    }
}
