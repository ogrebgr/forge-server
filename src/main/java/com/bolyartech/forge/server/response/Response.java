package com.bolyartech.forge.server.response;

import javax.annotation.Nonnull;
import javax.servlet.http.HttpServletResponse;


/**
 * Response of a RouteHandler
 * <p>
 * Route handlers handle HTTP requests and produce result of type Response
 */
public interface Response {
    int MIN_SIZE_FOR_GZIP = 500;

    /**
     * Converts Response to HttpServletResponse
     *
     * @param resp HTTP servlet response
     * @throws ResponseException if there is a problem during converting
     */
    long toServletResponse(@Nonnull HttpServletResponse resp) throws ResponseException;
}
