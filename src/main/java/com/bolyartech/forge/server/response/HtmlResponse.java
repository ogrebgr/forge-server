package com.bolyartech.forge.server.response;

import jakarta.servlet.http.Cookie;

import javax.annotation.Nonnull;
import java.util.List;


/**
 * HTML string response
 */
public class HtmlResponse extends AbstractStringResponse {
    private static final String CONTENT_TYPE_HTML = "text/html;charset=UTF-8";


    /**
     * Creates new HtmlResponse
     *
     * @param string HTML of the response
     */
    public HtmlResponse(@Nonnull String string) {
        super(string);
    }


    public HtmlResponse(List<Cookie> cookiesToSet, String string) {
        super(cookiesToSet, string);
    }


    public HtmlResponse(List<Cookie> cookiesToSet, List<HttpHeader> headersToAdd, String string, boolean enableGzipSupport) {
        super(cookiesToSet, headersToAdd, string, enableGzipSupport);
    }


    public HtmlResponse(List<Cookie> cookiesToSet, String string, boolean enableGzipSupport) {
        super(cookiesToSet, string, enableGzipSupport);
    }


    /**
     * Creates new HtmlResponse
     *
     * @param string            HTML of the response
     * @param enableGzipSupport if true Gzip compression will be used if the client supports it
     */
    public HtmlResponse(@Nonnull String string, boolean enableGzipSupport) {
        super(string, enableGzipSupport);
    }


    @Override
    protected String getContentType() {
        return CONTENT_TYPE_HTML;
    }
}
