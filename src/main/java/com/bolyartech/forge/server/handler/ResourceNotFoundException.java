package com.bolyartech.forge.server.handler;

public class ResourceNotFoundException extends Exception {
    public ResourceNotFoundException() {
    }


    public ResourceNotFoundException(String message) {
        super(message);
    }


    public ResourceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }


    public ResourceNotFoundException(Throwable cause) {
        super(cause);
    }
}
