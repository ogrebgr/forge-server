package com.bolyartech.forge.server.response.forge;

public class ServerErrorResponse extends ForgeResponse {
    public ServerErrorResponse() {
        super(BasicResponseCodes.Errors.INTERNAL_SERVER_ERROR, "");
    }


    public ServerErrorResponse(String string) {
        super(BasicResponseCodes.Errors.INTERNAL_SERVER_ERROR, string);
    }


    public ServerErrorResponse(String string, boolean enableGzipSupport) {
        super(BasicResponseCodes.Errors.INTERNAL_SERVER_ERROR, string, enableGzipSupport);
    }
}
