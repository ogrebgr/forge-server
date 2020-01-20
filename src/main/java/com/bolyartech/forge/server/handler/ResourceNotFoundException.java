package com.bolyartech.forge.server.handler;

import javax.annotation.Nonnull;


public class ResourceNotFoundException extends Exception {
    public ResourceNotFoundException() {
    }


    public ResourceNotFoundException(@Nonnull String message) {
        super(message);
    }


    public ResourceNotFoundException(@Nonnull String message, Throwable cause) {
        super(message, cause);
    }


    public ResourceNotFoundException(@Nonnull Throwable cause) {
        super(cause);
    }
}
