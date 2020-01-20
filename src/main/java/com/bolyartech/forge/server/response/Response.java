package com.bolyartech.forge.server.response;

import javax.annotation.Nonnull;
import javax.servlet.http.HttpServletResponse;


/**
 * Response of a RouteHandler
 * <p>
 * Route handlers handle HTTP requests and produce result of type Response
 */
public interface Response {
    /**
     * Converts Response to HttpServletResponse
     *
     * @param resp HTTP servlet response
     * @throws ResponseException if there is a problem during converting
     */
    void toServletResponse(@Nonnull HttpServletResponse resp) throws ResponseException;
}
