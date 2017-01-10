package com.bolyartech.forge.server.response.forge;

/**
 * Response with code <code>BasicResponseCodes.Errors.REQUIRES_HTTPS</code>
 */
public class RequiresHttpsResponse extends ForgeResponse {
    /**
     * Creates new RequiresHttpsResponse
     */
    public RequiresHttpsResponse() {
        super(BasicResponseCodes.Errors.REQUIRES_HTTPS, "Requires HTTPS");
    }
}
