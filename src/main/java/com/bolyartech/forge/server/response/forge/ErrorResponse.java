package com.bolyartech.forge.server.response.forge;

import javax.annotation.Nonnull;


/**
 * Error response
 * <p>
 * Use this class when you can't or don't want to use more specific error code
 */
public class ErrorResponse extends ForgeResponse {
    /**
     * Creates new ErrorResponse
     */
    public ErrorResponse() {
        super(BasicResponseCodes.Errors.ERROR, "");
    }


    /**
     * Creates new ErrorResponse
     *
     * @param string text to be shown
     */
    public ErrorResponse(@Nonnull String string) {
        super(BasicResponseCodes.Errors.ERROR, string);
    }
}
