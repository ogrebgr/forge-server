package com.bolyartech.forge.server.response.forge;

/**
 * Response with code BasicResponseCodes.Oks.OK
 */
public class OkResponse extends ForgeResponse {
    private static final OkResponse mInstance = new OkResponse();


    /**
     * Creates new OkResponse
     */
    public OkResponse() {
        super(BasicResponseCodes.Oks.OK, "");
    }


    /**
     * Creates new OkResponse
     *
     * @param string Text
     */
    public OkResponse(String string) {
        super(BasicResponseCodes.Oks.OK, string);
    }


    /**
     * Returns singleton instance
     *
     * @return OkResponse
     */
    public static OkResponse getInstance() {
        return mInstance;
    }
}
