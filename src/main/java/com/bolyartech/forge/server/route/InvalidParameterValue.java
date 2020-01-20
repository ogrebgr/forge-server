package com.bolyartech.forge.server.route;

import javax.annotation.Nonnull;

import static com.bolyartech.forge.server.misc.ForgeMessageFormat.format;


/**
 * This exception is thrown when request parameter (POST/GET/etc) contains invalid value
 */
public class InvalidParameterValue extends Exception {
    /**
     * Creates new InvalidParameterValue
     */
    public InvalidParameterValue() {
    }


    /**
     * Creates new InvalidParameterValue
     *
     * @param parameterName  Name of the parameter
     * @param parameterValue Value of the parameter
     */
    public InvalidParameterValue(@Nonnull String parameterName, @Nonnull String parameterValue) {
        super(format("Invalid parameter value {} for parameter {}",
                parameterValue, parameterName));
    }


    /**
     * Creates new InvalidParameterValue
     *
     * @param parameterName Value of the parameter
     */
    public InvalidParameterValue(@Nonnull String parameterName) {
        super(format("Invalid parameter value for parameter {}",
                parameterName));

    }
}
