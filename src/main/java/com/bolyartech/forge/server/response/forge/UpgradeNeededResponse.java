package com.bolyartech.forge.server.response.forge;

public class UpgradeNeededResponse extends ForgeResponse {
    public UpgradeNeededResponse() {
        super(BasicResponseCodes.Errors.UPGRADE_NEEDED, "");
    }


    public UpgradeNeededResponse(String string) {
        super(BasicResponseCodes.Errors.UPGRADE_NEEDED, string);
    }


    public UpgradeNeededResponse(String string, boolean enableGzipSupport) {
        super(BasicResponseCodes.Errors.UPGRADE_NEEDED, string, enableGzipSupport);
    }
}
