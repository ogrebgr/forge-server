package com.bolyartech.forge.server.response;

import com.bolyartech.forge.server.handler.RouteHandler;


/**
 * Thrown if there is a problem during handling HTTP request in a {@link RouteHandler} or during converting to
 * HttpServletResponse
 */
@SuppressWarnings("JavaDoc")
public class ResponseException extends RuntimeException {
    public ResponseException() {
    }


    public ResponseException(String message) {
        super(message);
    }


    public ResponseException(String message, Throwable cause) {
        super(message, cause);
    }


    public ResponseException(Throwable cause) {
        super(cause);
    }


    public ResponseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
