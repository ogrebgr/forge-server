package com.bolyartech.forge.server;

import javax.annotation.Nonnull;


/**
 * Enum for HTTP methods
 */
public enum HttpMethod {
    /**
     * GET method
     */
    GET("GET"),
    /**
     * POST method
     */
    POST("POST"),
    /**
     * PUT method
     */
    PUT("PUT"),
    /**
     * DELETE method
     */
    DELETE("DELETE");

    /**
     * String literal representation
     */
    private final String mLiteral;


    /**
     * Constructor
     *
     * @param literal String representation of the HTTP method
     */
    HttpMethod(@Nonnull String literal) {
        mLiteral = literal;
    }


    /**
     * Returns string literal representation
     *
     * @return string literal representation
     */
    public String getLiteral() {
        return mLiteral;
    }
}
