package com.bolyartech.forge.server.misc.basic_responses;

import com.bolyartech.forge.server.misc.BasicResponseCodes;
import com.bolyartech.forge.server.misc.ForgeResponse;


public class UpgradeNeededResponse extends ForgeResponse {
    public UpgradeNeededResponse(String payload) {
        super(BasicResponseCodes.Errors.UPGRADE_NEEDED.getCode(), payload);
    }


    public UpgradeNeededResponse() {
        super(BasicResponseCodes.Errors.UPGRADE_NEEDED.getCode(),
                "Client upgrade is needed. Current version is not supported");
    }
}
