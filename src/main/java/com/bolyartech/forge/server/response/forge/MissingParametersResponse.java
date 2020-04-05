package com.bolyartech.forge.server.response.forge;

import javax.annotation.Nonnull;


/**
 * Response indicating missing required parameters
 */
@Deprecated
public class MissingParametersResponse extends ForgeResponse {
    private static final MissingParametersResponse instance = new MissingParametersResponse();


    /**
     * Creates new MissingParametersResponse
     */
    public MissingParametersResponse() {
        super(BasicResponseCodes.Errors.MISSING_PARAMETERS, "MISSING_PARAMETERS");
    }


    /**
     * Creates new MissingParametersResponse
     *
     * @param string Text. Here you may provide more details which parameters are missing
     */
    public MissingParametersResponse(@Nonnull String string) {
        super(BasicResponseCodes.Errors.MISSING_PARAMETERS, string);
    }


    /**
     * Returns singleton instance
     *
     * @return Instance of MissingParametersResponse
     */
    public static MissingParametersResponse getInstance() {
        return instance;
    }
}
