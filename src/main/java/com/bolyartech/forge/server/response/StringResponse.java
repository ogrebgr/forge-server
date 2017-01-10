package com.bolyartech.forge.server.response;

/**
 * String reponse
 */
public interface StringResponse extends Response {
    /**
     * Returns the string of the response
     *
     * @return string
     */
    String getString();
}
