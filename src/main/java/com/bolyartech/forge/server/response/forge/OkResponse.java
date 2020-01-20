package com.bolyartech.forge.server.response.forge;

import javax.annotation.Nonnull;


/**
 * Response with code BasicResponseCodes.Oks.OK
 */
public class OkResponse extends ForgeResponse {
    private static final OkResponse instance = new OkResponse();


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
    public OkResponse(@Nonnull String string) {
        super(BasicResponseCodes.Oks.OK, string);
    }


    /**
     * Returns singleton instance
     *
     * @return OkResponse
     */
    public static OkResponse getInstance() {
        return instance;
    }
}
