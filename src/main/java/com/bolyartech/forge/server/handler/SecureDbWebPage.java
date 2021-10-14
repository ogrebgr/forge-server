package com.bolyartech.forge.server.handler;

import com.bolyartech.forge.server.db.DbPool;
import com.bolyartech.forge.server.misc.TemplateEngine;
import com.bolyartech.forge.server.misc.TemplateEngineFactory;
import com.bolyartech.forge.server.response.Response;
import com.bolyartech.forge.server.response.forge.RequiresHttpsResponse;
import com.bolyartech.forge.server.route.RequestContext;

import java.sql.Connection;


/**
 * Web page that must be accessed via HTTPS and use database connection to process the request
 *
 * @deprecated If you need to prevent http access just disable it in jetty.conf (http_port=0)
 */
abstract public class SecureDbWebPage extends DbWebPage {
    /**
     * Creates new SecureDbWebPage
     *
     * @param templateEngineFactory template engine to be used
     * @param dbPool                DB pool
     */
    public SecureDbWebPage(TemplateEngineFactory templateEngineFactory, DbPool dbPool) {
        this(templateEngineFactory, false, dbPool);
    }


    /**
     * Creates new SecureDbWebPage
     *
     * @param templateEngineFactory template engine to be used
     * @param enableGzipSupport     if true Gzip will be enabled
     * @param dbPool                DB pool
     */
    public SecureDbWebPage(TemplateEngineFactory templateEngineFactory,
                           @SuppressWarnings("SameParameterValue") boolean enableGzipSupport,
                           DbPool dbPool) {

        super(templateEngineFactory, enableGzipSupport, dbPool);
    }


    /**
     * Handles the HTTP request and produces HTML
     *
     * @param ctx  Request context
     * @param tple Template engine to be used
     * @param dbc  Database connection
     * @return produced HTML
     */
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
