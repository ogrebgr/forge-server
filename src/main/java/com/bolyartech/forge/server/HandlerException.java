package com.bolyartech.forge.server;

/**
 * Thrown when handler is unable to process thje request
 */
public class HandlerException extends Exception {
    public HandlerException() {
    }


    public HandlerException(String message) {
        super(message);
    }


    public HandlerException(String message, Throwable cause) {
        super(message, cause);
    }


    public HandlerException(Throwable cause) {
        super(cause);
    }


    public HandlerException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
