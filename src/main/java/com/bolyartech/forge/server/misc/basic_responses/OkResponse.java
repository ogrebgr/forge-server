package com.bolyartech.forge.server.misc.basic_responses;

import com.bolyartech.forge.server.misc.BasicResponseCodes;
import com.bolyartech.forge.server.misc.ForgeResponse;


public class OkResponse extends ForgeResponse {
    public OkResponse(String payload) {
        super(BasicResponseCodes.Oks.OK.getCode(), payload);
    }

    public OkResponse() {
        super(BasicResponseCodes.Oks.OK.getCode(), "OK");
    }
}
