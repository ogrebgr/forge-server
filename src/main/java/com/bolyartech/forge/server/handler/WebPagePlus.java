package com.bolyartech.forge.server.handler;

import com.bolyartech.forge.server.misc.TemplateEngine;
import com.bolyartech.forge.server.misc.TemplateEngineFactory;
import com.bolyartech.forge.server.response.*;
import com.bolyartech.forge.server.route.RequestContext;

import javax.annotation.Nonnull;


/**
 * Handler which provides normal web page functionality.
 * Use {@link #createHtmlResponse(String)} to create HTML response.
 * Use {@link #createHtmlResponse(String)} to create redirect response.
 */
abstract public class WebPagePlus implements RouteHandler {
    private final TemplateEngineFactory templateEngineFactory;
    private final boolean enableGzipSupport;


    /**
     * Creates new WebPagePlus
     *
     * @param templateEngineFactory Template engine factory
     */
    public WebPagePlus(@Nonnull TemplateEngineFactory templateEngineFactory) {
        this.templateEngineFactory = templateEngineFactory;
        enableGzipSupport = false;
    }


    /**
     * Creates new WebPagePlus
     *
     * @param templateEngineFactory Template engine factory
     * @param enableGzipSupport     if true Gzip compression will be used (if supported by the client)
     */
    public WebPagePlus(@Nonnull TemplateEngineFactory templateEngineFactory, boolean enableGzipSupport) {
        this.templateEngineFactory = templateEngineFactory;
        this.enableGzipSupport = enableGzipSupport;
    }


    /**
     * Handles a HTTP request wrapped as {@link RequestContext} and produces Response
     *
     * @param ctx  Request context
     * @param tple Template engine
     * @return Response
     * @throws ResponseException if there is a problem handling the request
     */
    abstract public Response handlePage(RequestContext ctx, TemplateEngine tple) throws ResponseException;


    @Override
    public Response handle(@Nonnull RequestContext ctx) {
        return handlePage(ctx, templateEngineFactory.createNew());
    }


    /**
     * Returns the template engine factory
     *
     * @return template engine factory
     */
    public TemplateEngineFactory getTemplateEngineFactory() {
        return templateEngineFactory;
    }


    /**
     * Convenience method for creating HtmlResponse
     * @param content
     * @return
     */
    public HtmlResponse createHtmlResponse(String content) {
        return new HtmlResponse(content, enableGzipSupport);
    }


    /**
     * Convenience method for creating redirect response (302 Moved permanently/Found)
     * @param location
     * @return
     */
    public RedirectResponse createRedirectResponse(String location) {
        return new RedirectResponseMovedTemporarily(location);
    }
}
