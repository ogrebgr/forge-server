package com.bolyartech.forge.server.handler;

import com.bolyartech.forge.server.response.Response;
import com.bolyartech.forge.server.response.ResponseException;
import com.bolyartech.forge.server.route.RequestContext;
import com.bolyartech.forge.server.route.Route;

import javax.annotation.Nonnull;


/**
 * RouteHandler of HTTP request
 * Each {@link Route} is defined by HttpMethod, path and handler. When a HTTP request matches the method and the path
 * the handler is used to handleForgeDb the request and produce a {@link Response}.
 * <p>
 * There are two types of classes that implement RouteHandler. First one is the 'web page' type, i.e. handlers that
 * produces HTML output which is meant to be consumed by a browser. Second one is the 'endpoint' type, i.e.
 * handlers that produce non-HTML output like JSON or binary which is meant to be used by non-browser clients.
 */
public interface RouteHandler {
    /**
     * Handles HTTP request data wrapped in {@link RequestContext} object and produces {@link Response}
     *
     * @param ctx Request context
     * @return Response
     * @throws ResponseException if there is problem handling the request
     */
    Response handle(@Nonnull RequestContext ctx) throws ResponseException, StaticResourceNotFoundException;
}
