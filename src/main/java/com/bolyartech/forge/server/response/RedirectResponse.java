package com.bolyartech.forge.server.response;


/**
 * Redirect response which instructs the browsers to redirect
 */
public interface RedirectResponse extends Response {
    /**
     * Returns target location
     *
     * @return target location to where to redirect
     */
    String getLocation();
}
