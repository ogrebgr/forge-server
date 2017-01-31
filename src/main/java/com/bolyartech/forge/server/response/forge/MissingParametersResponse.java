package com.bolyartech.forge.server.response.forge;

/**
 * Response indicating missing required parameters
 */
public class MissingParametersResponse extends ForgeResponse {
    private static final MissingParametersResponse mInstance = new MissingParametersResponse();


    /**
     * Creates new MissingParametersResponse
     */
    public MissingParametersResponse() {
        super(BasicResponseCodes.Errors.MISSING_PARAMETERS, "");
    }


    /**
     * Creates new MissingParametersResponse
     *
     * @param string Text. Here you may provide more details which parameters are missing
     */
    public MissingParametersResponse(String string) {
        super(BasicResponseCodes.Errors.MISSING_PARAMETERS, string);
    }


    /**
     * Returns singleton instance
     *
     * @return Instance of MissingParametersResponse
     */
    public static MissingParametersResponse getInstance() {
        return mInstance;
    }
}