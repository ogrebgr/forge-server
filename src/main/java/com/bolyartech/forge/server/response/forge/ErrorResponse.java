package com.bolyartech.forge.server.response.forge;

public class ErrorResponse extends ForgeResponse {
    public ErrorResponse() {
        super(BasicResponseCodes.Errors.ERROR, "");
    }


    public ErrorResponse(String string) {
        super(BasicResponseCodes.Errors.ERROR, string);
    }


    public ErrorResponse(String string, boolean enableGzipSupport) {
        super(BasicResponseCodes.Errors.ERROR, string, enableGzipSupport);
    }
}
