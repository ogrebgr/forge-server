package com.bolyartech.forge.server.response;

/**
 * JSON string response
 */
public class JsonResponse extends AbstractStringResponse {
    private static final String CONTENT_TYPE_JSON = "application/json;charset=UTF-8";


    /**
     * Creates new JsonResponse
     *
     * @param string String response, i.e. the JSON
     */
    public JsonResponse(String string) {
        super(string);
    }


    /**
     * Creates new JsonResponse
     *
     * @param string            String response, i.e. the JSON
     * @param enableGzipSupport if true Gzip compression will be used if the client supports it
     */
    public JsonResponse(String string, boolean enableGzipSupport) {
        super(string, enableGzipSupport);
    }


    @Override
    protected String getContentType() {
        return CONTENT_TYPE_JSON;
    }
}
