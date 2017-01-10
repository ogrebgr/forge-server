package com.bolyartech.forge.server.response.forge;


/**
 * Response with code <code>BasicResponseCodes.Errors.UPGRADE_NEEDED</code>
 */
public class UpgradeNeededResponse extends ForgeResponse {
    /**
     * Creates new UpgradeNeededResponse
     */
    public UpgradeNeededResponse() {
        super(BasicResponseCodes.Errors.UPGRADE_NEEDED, "Upgrade needed");
    }


    /**
     * Creates new UpgradeNeededResponse
     *
     * @param string Here you can specify more info about what version is needed or how to upgrade
     */
    public UpgradeNeededResponse(String string) {
        super(BasicResponseCodes.Errors.UPGRADE_NEEDED, string);
    }
}
