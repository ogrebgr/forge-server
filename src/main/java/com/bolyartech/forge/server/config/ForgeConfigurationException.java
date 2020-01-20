package com.bolyartech.forge.server.config;

import javax.annotation.Nonnull;


/**
 * Thrown when there is a problem with the configuration data
 */
@SuppressWarnings("JavaDoc")
public class ForgeConfigurationException extends Exception {
    public ForgeConfigurationException() {
    }


    public ForgeConfigurationException(@Nonnull String message) {
        super(message);
    }


    public ForgeConfigurationException(@Nonnull String message, @Nonnull Throwable cause) {
        super(message, cause);
    }


    public ForgeConfigurationException(@Nonnull Throwable cause) {
        super(cause);
    }


    public ForgeConfigurationException(@Nonnull String message, @Nonnull Throwable cause, @Nonnull boolean enableSuppression, @Nonnull boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
