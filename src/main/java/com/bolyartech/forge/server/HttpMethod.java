package com.bolyartech.forge.server;

/**
 * Enum for HTTP methods
 */
public enum HttpMethod {
    GET("GET"), POST("POST"), PUT("PUT"), DELETE("DELETE");

    private final String mLiteral;


    HttpMethod(String literal) {
        mLiteral = literal;
    }


    public String getLiteral() {
        return mLiteral;
    }
}
