package com.bolyartech.forge.server.response.forge;

import javax.annotation.Nonnull;


/**
 * Invalid parameter value response
 */
@Deprecated
public class InvalidParameterValueResponse extends ForgeResponse {
    private static final InvalidParameterValueResponse instance = new InvalidParameterValueResponse();

    /**
     * Creates new InvalidParameterValueResponse
     */
    public InvalidParameterValueResponse() {
        super(BasicResponseCodes.Errors.INVALID_PARAMETER_VALUE, "INVALID_PARAMETER_VALUE");
    }


    /**
     * Creates new InvalidParameterValueResponse
     *
     * @param string Text. Here you may provide more info about what is wrong with the parameter
     */
    public InvalidParameterValueResponse(@Nonnull String string) {
        super(BasicResponseCodes.Errors.INVALID_PARAMETER_VALUE, string);
    }


    public static InvalidParameterValueResponse getInstance() {
        return instance;
    }
}
