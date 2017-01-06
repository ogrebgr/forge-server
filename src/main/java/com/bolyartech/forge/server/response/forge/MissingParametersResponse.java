package com.bolyartech.forge.server.response.forge;

public class MissingParametersResponse extends ForgeResponse {
    private static MissingParametersResponse mInstance = new MissingParametersResponse();


    public MissingParametersResponse() {
        super(BasicResponseCodes.Errors.MISSING_PARAMETERS, "");
    }


    public MissingParametersResponse(String string) {
        super(BasicResponseCodes.Errors.MISSING_PARAMETERS, string);
    }


    public MissingParametersResponse(String string, boolean enableGzipSupport) {
        super(BasicResponseCodes.Errors.MISSING_PARAMETERS, string, enableGzipSupport);
    }


    public static MissingParametersResponse getInstance() {
        return mInstance;
    }
}
