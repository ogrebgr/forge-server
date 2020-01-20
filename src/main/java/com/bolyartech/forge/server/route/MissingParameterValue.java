package com.bolyartech.forge.server.route;

import javax.annotation.Nonnull;


/**
 * This exception is thrown when one or more parameters are missing from GET/POST/etc HTTP request
 */
public class MissingParameterValue extends Exception {
    /**
     * Creates new MissingParameterValue
     */
    public MissingParameterValue() {
    }


    /**
     * Creates new MissingParameterValue
     *
     * @param parameterName Name of the parameter
     */
    public MissingParameterValue(@Nonnull String parameterName) {
        super("Missing value for parameter " + parameterName);
    }
}
