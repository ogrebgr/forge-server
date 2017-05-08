package com.bolyartech.forge.server.route;

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
    public MissingParameterValue(String parameterName) {
        super("Missing value for parameter " + parameterName);
    }
}
