package com.bolyartech.forge.server.response;

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
    public HtmlResponse(String string) {
        super(string);
    }


    /**
     * Creates new HtmlResponse
     *
     * @param string            HTML of the response
     * @param enableGzipSupport if true Gzip compression will be used if the client supports it
     */
    public HtmlResponse(String string, boolean enableGzipSupport) {
        super(string, enableGzipSupport);
    }


    @Override
    protected String getContentType() {
        return CONTENT_TYPE_HTML;
    }
}
