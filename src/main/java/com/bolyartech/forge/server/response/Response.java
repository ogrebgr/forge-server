package com.bolyartech.forge.server.response;

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
    void toServletResponse(HttpServletResponse resp) throws ResponseException;
}
