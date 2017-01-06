package com.bolyartech.forge.server.response.forge;

public class InvalidParameterValueResponse extends ForgeResponse {
    public InvalidParameterValueResponse() {
        super(BasicResponseCodes.Errors.INVALID_PARAMETER_VALUE, "");
    }


    public InvalidParameterValueResponse(String string) {
        super(BasicResponseCodes.Errors.INVALID_PARAMETER_VALUE, string);
    }


    public InvalidParameterValueResponse(String string, boolean enableGzipSupport) {
        super(BasicResponseCodes.Errors.INVALID_PARAMETER_VALUE, string, enableGzipSupport);
    }

}
