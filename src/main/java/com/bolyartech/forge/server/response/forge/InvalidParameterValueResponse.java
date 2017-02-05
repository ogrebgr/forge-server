package com.bolyartech.forge.server.response.forge;

/**
 * Invalid parameter value response
 */
public class InvalidParameterValueResponse extends ForgeResponse {
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
    public InvalidParameterValueResponse(String string) {
        super(BasicResponseCodes.Errors.INVALID_PARAMETER_VALUE, string);
    }
}
