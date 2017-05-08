package com.bolyartech.forge.server.handler;

import com.bolyartech.forge.server.misc.TemplateEngine;
import com.bolyartech.forge.server.misc.TemplateEngineFactory;
import com.bolyartech.forge.server.response.HtmlResponse;
import com.bolyartech.forge.server.response.Response;
import com.bolyartech.forge.server.response.forge.RequiresHttpsResponse;
import com.bolyartech.forge.server.route.RequestContext;


/**
 * Web page that <b>must</b> be accessed via HTTPS
 */
abstract public class SecureWebPage implements RouteHandler {
    private final TemplateEngineFactory mTemplateEngineFactory;
    private final boolean mEnableGzipSupport;


    /**
     * Creates new SecureWebPage
     *
     * @param templateEngineFactory Template engine factory
     * @param enableGzipSupport     if true GZIP compression will be enabled (if available)
     */
    public SecureWebPage(TemplateEngineFactory templateEngineFactory, @SuppressWarnings("SameParameterValue") boolean enableGzipSupport) {
        mTemplateEngineFactory = templateEngineFactory;
        mEnableGzipSupport = enableGzipSupport;
    }


    /**
     * Creates new SecureWebPage
     * @param templateEngineFactory Template engine factory
     */
    public SecureWebPage(TemplateEngineFactory templateEngineFactory) {
        this(templateEngineFactory, false);
    }


    /**
     * Handles HTTPS request and produces HTML
     *
     * @param ctx  Context
     * @param tple template engine to be used
     * @return produced HTML
     */
    abstract public String produceHtmlSecure(RequestContext ctx, TemplateEngine tple);


    @Override
    public Response handle(RequestContext ctx) {
        if (ctx.getScheme().equals("https")) {
            String content = produceHtmlSecure(ctx, mTemplateEngineFactory.createNew());

            return new HtmlResponse(content, mEnableGzipSupport);
        } else {
            return new RequiresHttpsResponse();
        }
    }
}
