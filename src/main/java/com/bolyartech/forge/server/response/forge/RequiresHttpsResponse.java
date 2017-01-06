package com.bolyartech.forge.server.response.forge;

public class RequiresHttpsResponse extends ForgeResponse {
    public RequiresHttpsResponse() {
        super(BasicResponseCodes.Errors.REQUIRES_HTTPS, "");
    }


    public RequiresHttpsResponse(String string) {
        super(BasicResponseCodes.Errors.REQUIRES_HTTPS, string);
    }


    public RequiresHttpsResponse(String string, boolean enableGzipSupport) {
        super(BasicResponseCodes.Errors.REQUIRES_HTTPS, string, enableGzipSupport);
    }
}
