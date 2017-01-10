package com.bolyartech.forge.server.handler;

import com.bolyartech.forge.server.misc.TemplateEngine;
import com.bolyartech.forge.server.misc.TemplateEngineFactory;
import com.bolyartech.forge.server.response.HtmlResponse;
import com.bolyartech.forge.server.response.Response;
import com.bolyartech.forge.server.route.RequestContext;


/**
 * Handler that produces HTML content, i.e. web page
 */
abstract public class WebPage implements RouteHandler {
    private final TemplateEngineFactory mTemplateEngineFactory;
    private final boolean mEnableGzipSupport;


    /**
     * Creates new WebPage
     *
     * @param templateEngineFactory Template engine factory
     */
    public WebPage(TemplateEngineFactory templateEngineFactory) {
        mTemplateEngineFactory = templateEngineFactory;
        mEnableGzipSupport = false;
    }


    /**
     * Creates new WebPage
     *
     * @param templateEngineFactory Template engine factory
     * @param enableGzipSupport     if true Gzip compression will be used (if supported by the client)
     */
    public WebPage(TemplateEngineFactory templateEngineFactory, boolean enableGzipSupport) {
        mTemplateEngineFactory = templateEngineFactory;
        mEnableGzipSupport = enableGzipSupport;
    }


    /**
     * Handles a HTTP request wrapped as {@link RequestContext} and produces HTML
     *
     * @param ctx  Request context
     * @param tple Template engine
     * @return HTML
     */
    abstract public String produceHtml(RequestContext ctx, TemplateEngine tple);


    @Override
    public Response handle(RequestContext ctx) {
        String content = produceHtml(ctx, mTemplateEngineFactory.createNew());

        return new HtmlResponse(content, mEnableGzipSupport);
    }
}
