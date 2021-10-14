package com.bolyartech.forge.server.response;

import jakarta.servlet.http.Cookie;

import javax.annotation.Nonnull;
import java.util.List;


/**
 * Plain text string response (UTF-8 encoded)
 */
public class PlainTextResponse extends AbstractStringResponse {
    private static final String CONTENT_TYPE_TEXT = "text/plain;charset=UTF-8";


    /**
     * Creates new PlainTextResponse
     *
     * @param string Content of the response, i.e. the text
     */
    public PlainTextResponse(String string) {
        super(string);
    }


    public PlainTextResponse(List<Cookie> cookiesToSet, String string) {
        super(cookiesToSet, string);
    }


    public PlainTextResponse(List<Cookie> cookiesToSet, String string, boolean enableGzipSupport) {
        super(cookiesToSet, string, enableGzipSupport);
    }


    public PlainTextResponse(List<Cookie> cookiesToSet, List<HttpHeader> headersToAdd, String string, boolean enableGzipSupport) {
        super(cookiesToSet, headersToAdd, string, enableGzipSupport);
    }


    /**
     * Creates new PlainTextResponse
     *
     * @param string            Content of the response, i.e. the text
     * @param enableGzipSupport if true Gzip compression will be used if the client supports it
     */
    public PlainTextResponse(@Nonnull String string, boolean enableGzipSupport) {
        super(string, enableGzipSupport);
    }


    @Override
    protected String getContentType() {
        return CONTENT_TYPE_TEXT;
    }
}
