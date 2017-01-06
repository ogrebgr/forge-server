package com.bolyartech.forge.server.response;

public class HtmlResponse extends AbstractStringResponse {
    private static final String CONTENT_TYPE_HTML = "text/html;charset=UTF-8";


    public HtmlResponse(String string) {
        super(string);
    }


    public HtmlResponse(String string, boolean enableGzipSupport) {
        super(string, enableGzipSupport);
    }


    @Override
    protected String getContentType() {
        return CONTENT_TYPE_HTML;
    }
}
