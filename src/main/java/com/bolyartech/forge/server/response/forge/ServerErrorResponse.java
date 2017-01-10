package com.bolyartech.forge.server.response.forge;

/**
 * Response with <code>BasicResponseCodes.Errors.INTERNAL_SERVER_ERROR</code>
 */
public class ServerErrorResponse extends ForgeResponse {
    /**
     * Creates new ServerErrorResponse
     */
    public ServerErrorResponse() {
        super(BasicResponseCodes.Errors.INTERNAL_SERVER_ERROR, "Server error");
    }
}
