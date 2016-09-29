package com.bolyartech.forge.server.misc.basic_responses;

import com.bolyartech.forge.server.misc.BasicResponseCodes;
import com.bolyartech.forge.server.misc.ForgeResponse;


public class ErrorResponse extends ForgeResponse {
    public ErrorResponse(String payload) {
        super(BasicResponseCodes.Errors.ERROR.getCode(), payload);
    }


    public ErrorResponse() {
        super(BasicResponseCodes.Errors.ERROR.getCode(), "Error");
    }
}
