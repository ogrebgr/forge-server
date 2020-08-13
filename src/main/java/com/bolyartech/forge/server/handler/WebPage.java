package com.bolyartech.forge.server.handler;

import com.bolyartech.forge.server.misc.TemplateEngine;
import com.bolyartech.forge.server.misc.TemplateEngineFactory;
import com.bolyartech.forge.server.response.HtmlResponse;
import com.bolyartech.forge.server.response.Response;
import com.bolyartech.forge.server.response.ResponseException;
import com.bolyartech.forge.server.route.RequestContext;

import javax.annotation.Nonnull;


/**
 * Handler that produces HTML content, i.e. simple web page
 */
abstract public class WebPage implements RouteHandler {
    private final TemplateEngineFactory templateEngineFactory;
    private final boolean enableGzipSupport;


    /**
     * Creates new WebPage
     *
     * @param templateEngineFactory Template engine factory
     */
    public WebPage(@Nonnull TemplateEngineFactory templateEngineFactory) {
        this.templateEngineFactory = templateEngineFactory;
        enableGzipSupport = false;
    }


    /**
     * Creates new WebPage
     *
     * @param templateEngineFactory Template engine factory
     * @param enableGzipSupport     if true Gzip compression will be used (if supported by the client)
     */
    public WebPage(@Nonnull TemplateEngineFactory templateEngineFactory, boolean enableGzipSupport) {
        this.templateEngineFactory = templateEngineFactory;
        this.enableGzipSupport = enableGzipSupport;
    }


    /**
     * Handles a HTTP request wrapped as {@link RequestContext} and produces HTML
     *
     * @param ctx  Request context
     * @param tple Template engine
     * @return HTML
     * @throws ResponseException if there is a problem handling the request
     */
    abstract public String produceHtml(RequestContext ctx, TemplateEngine tple) throws ResponseException;


    @Override
    public Response handle(@Nonnull RequestContext ctx) {
        String content = produceHtml(ctx, templateEngineFactory.createNew());

        return new HtmlResponse(content, enableGzipSupport);
    }


    /**
     * Returns the template engine factory
     *
     * @return template engine factory
     */
    public TemplateEngineFactory getTemplateEngineFactory() {
        return templateEngineFactory;
    }
}
