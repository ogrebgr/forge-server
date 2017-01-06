package com.bolyartech.forge.server.response.forge;

public class OkResponse extends ForgeResponse {
    private static OkResponse mInstance = new OkResponse();


    public OkResponse() {
        super(BasicResponseCodes.Oks.OK, "");
    }


    public OkResponse(String string) {
        super(BasicResponseCodes.Oks.OK, string);
    }


    public OkResponse(String string, boolean enableGzipSupport) {
        super(BasicResponseCodes.Oks.OK, string, enableGzipSupport);
    }


    public static OkResponse getInstance() {
        return mInstance;
    }
}
