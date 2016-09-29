package com.bolyartech.forge.server.misc.basic_responses;

import com.bolyartech.forge.server.misc.BasicResponseCodes;
import com.bolyartech.forge.server.misc.ForgeResponse;


public class InternalServerErrorResponse extends ForgeResponse {
    public InternalServerErrorResponse(String payload) {
        super(BasicResponseCodes.Errors.INTERNAL_SERVER_ERROR.getCode(), payload);
    }

    public InternalServerErrorResponse() {
        super(BasicResponseCodes.Errors.INTERNAL_SERVER_ERROR.getCode(), "Internal server error");
    }
}
