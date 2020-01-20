package com.bolyartech.forge.server.handler;

import com.bolyartech.forge.server.misc.TemplateEngine;
import com.bolyartech.forge.server.route.RequestContext;

import javax.annotation.Nonnull;
import java.sql.Connection;


/**
 * Web page that uses database connection to handle the request
 */
public interface DbWebPageInterface extends RouteHandler {

    /**
     * Handles the request and produces HTML
     *
     * @param ctx  Request context
     * @param tple Template engine to be used
     * @param dbc  Database connection
     * @return Produced HTML
     */
    @SuppressWarnings("EmptyMethod")
    String produceHtml(@Nonnull RequestContext ctx, @Nonnull TemplateEngine tple, @Nonnull Connection dbc);
}
