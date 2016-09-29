package com.bolyartech.forge.server.misc.basic_responses;

import com.bolyartech.forge.server.misc.BasicResponseCodes;
import com.bolyartech.forge.server.misc.ForgeResponse;


public class InvalidParameterValueResponse extends ForgeResponse {
    public InvalidParameterValueResponse(String payload) {
        super(BasicResponseCodes.Errors.INVALID_PARAMETER_VALUE.getCode(), payload);
    }


    public InvalidParameterValueResponse() {
        super(BasicResponseCodes.Errors.INVALID_PARAMETER_VALUE.getCode(), "Invalid parameter value");
    }
}
