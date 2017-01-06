package com.bolyartech.forge.server.config;

public class ForgeConfigurationException extends Exception {
    public ForgeConfigurationException() {
    }


    public ForgeConfigurationException(String message) {
        super(message);
    }


    public ForgeConfigurationException(String message, Throwable cause) {
        super(message, cause);
    }


    public ForgeConfigurationException(Throwable cause) {
        super(cause);
    }


    public ForgeConfigurationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
