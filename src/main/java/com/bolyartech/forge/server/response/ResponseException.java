package com.bolyartech.forge.server.response;

import com.bolyartech.forge.server.handler.RouteHandler;

import javax.annotation.Nonnull;


/**
 * Thrown if there is a problem during handling HTTP request in a {@link RouteHandler} or during converting to
 * HttpServletResponse
 */
@SuppressWarnings("JavaDoc")
public class ResponseException extends RuntimeException {
    public ResponseException() {
    }


    public ResponseException(@Nonnull String message) {
        super(message);
    }


    public ResponseException(@Nonnull String message, Throwable cause) {
        super(message, cause);
    }


    public ResponseException(@Nonnull Throwable cause) {
        super(cause);
    }


    public ResponseException(@Nonnull String message, @Nonnull Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
