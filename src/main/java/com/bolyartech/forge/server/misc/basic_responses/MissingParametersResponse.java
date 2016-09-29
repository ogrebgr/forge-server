package com.bolyartech.forge.server.misc.basic_responses;

import com.bolyartech.forge.server.misc.BasicResponseCodes;
import com.bolyartech.forge.server.misc.ForgeResponse;


public class MissingParametersResponse extends ForgeResponse {
    public MissingParametersResponse(String payload) {
        super(BasicResponseCodes.Errors.MISSING_PARAMETERS.getCode(), payload);
    }

    public MissingParametersResponse() {
        super(BasicResponseCodes.Errors.MISSING_PARAMETERS.getCode(), "Missing required parameters");
    }
}
