package com.bolyartech.forge.server.route;

import com.bolyartech.forge.server.HttpMethod;
import com.bolyartech.forge.server.handler.StaticResourceNotFoundException;
import com.bolyartech.forge.server.response.ResponseException;

import javax.annotation.Nonnull;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * HTTP route
 */
public interface Route {
    /**
     * Returns HTTP method
     *
     * @return HTTP method
     */
    HttpMethod getHttpMethod();

    /**
     * Returns route's path
     *
     * @return route's path
     */
    String getPath();

    /**
     * Handles HTTP request
     *
     * @param req      HTTP request
     * @param response HTTP servlet response
     * @throws ResponseException if there is a problem handling the request
     */
    void handle(@Nonnull HttpServletRequest req, @Nonnull HttpServletResponse response)
            throws ResponseException;
}
