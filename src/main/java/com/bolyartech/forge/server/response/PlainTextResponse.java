package com.bolyartech.forge.server.response;

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


    /**
     * Creates new PlainTextResponse
     *
     * @param string            Content of the response, i.e. the text
     * @param enableGzipSupport if true Gzip compression will be used if the client supports it
     */
    public PlainTextResponse(String string, boolean enableGzipSupport) {
        super(string, enableGzipSupport);
    }


    @Override
    protected String getContentType() {
        return CONTENT_TYPE_TEXT;
    }
}
