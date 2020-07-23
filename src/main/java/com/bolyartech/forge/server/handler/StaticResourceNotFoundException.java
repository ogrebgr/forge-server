package com.bolyartech.forge.server.handler;

import javax.annotation.Nonnull;


public class StaticResourceNotFoundException extends Exception {
    public StaticResourceNotFoundException() {
    }


    public StaticResourceNotFoundException(@Nonnull String message) {
        super(message);
    }


    public StaticResourceNotFoundException(@Nonnull String message, Throwable cause) {
        super(message, cause);
    }


    public StaticResourceNotFoundException(@Nonnull Throwable cause) {
        super(cause);
    }
}
