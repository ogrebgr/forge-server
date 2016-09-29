package com.bolyartech.forge.server.misc.basic_responses;

import com.bolyartech.forge.server.misc.BasicResponseCodes;
import com.bolyartech.forge.server.misc.ForgeResponse;


public class RequiresHttpsResponse extends ForgeResponse {
    public RequiresHttpsResponse(String payload) {
        super(BasicResponseCodes.Errors.REQUIRES_HTTPS.getCode(), payload);
    }


    public RequiresHttpsResponse() {
        super(BasicResponseCodes.Errors.REQUIRES_HTTPS.getCode(), "Connection have to be over HTTPS");
    }
}
