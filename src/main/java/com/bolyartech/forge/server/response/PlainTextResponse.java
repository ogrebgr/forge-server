package com.bolyartech.forge.server.response;

public class PlainTextResponse extends AbstractStringResponse {
    private static final String CONTENT_TYPE_TEXT = "text/plain;charset=UTF-8";


    public PlainTextResponse(String string) {
        super(string);
    }


    @Override
    protected String getContentType() {
        return CONTENT_TYPE_TEXT;
    }
}
