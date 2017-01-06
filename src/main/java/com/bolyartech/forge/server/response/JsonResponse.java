package com.bolyartech.forge.server.response;


public class JsonResponse extends AbstractStringResponse {
    private static final String CONTENT_TYPE_JSON = "application/json;charset=UTF-8";


    public JsonResponse(String string) {
        super(string);
    }


    public JsonResponse(String string, boolean enableGzipSupport) {
        super(string, enableGzipSupport);
    }


    @Override
    protected String getContentType() {
        return CONTENT_TYPE_JSON;
    }
}
