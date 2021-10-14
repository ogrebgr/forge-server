package com.bolyartech.forge.server.response;

import jakarta.servlet.http.Cookie;

import javax.annotation.Nonnull;
import java.util.List;


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
    public JsonResponse(@Nonnull String string) {
        super(string);
    }


    public JsonResponse(List<Cookie> cookiesToSet, String string) {
        super(cookiesToSet, string);
    }


    public JsonResponse(List<Cookie> cookiesToSet, String string, boolean enableGzipSupport) {
        super(cookiesToSet, string, enableGzipSupport);
    }


    /**
     * Creates new JsonResponse
     *
     * @param string            String response, i.e. the JSON
     * @param enableGzipSupport if true Gzip compression will be used if the client supports it
     */
    public JsonResponse(@Nonnull String string, boolean enableGzipSupport) {
        super(string, enableGzipSupport);
    }


    @Override
    protected String getContentType() {
        return CONTENT_TYPE_JSON;
    }
}
