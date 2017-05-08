package com.bolyartech.forge.server.response;

/**
 * String response
 */
public interface StringResponse extends Response {
    /**
     * Returns the string of the response
     *
     * @return string
     */
    String getString();
}
