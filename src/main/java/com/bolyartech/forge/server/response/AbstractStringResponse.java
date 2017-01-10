package com.bolyartech.forge.server.response;

import com.google.common.io.ByteStreams;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.GZIPOutputStream;


/**
 * Base class for specialized string responses
 */
abstract public class AbstractStringResponse implements StringResponse {
    private final String mString;
    private final boolean mEnableGzipSupport;


    /**
     * Creates new AbstractStringResponse
     *
     * @param string String of the response
     */
    public AbstractStringResponse(String string) {
        mString = string;
        mEnableGzipSupport = false;
    }


    /**
     * Creates new AbstractStringResponse
     *
     * @param string            String of the response
     * @param enableGzipSupport if true Gzip compression will be used if the client supports it
     */
    public AbstractStringResponse(String string, boolean enableGzipSupport) {
        mString = string;
        mEnableGzipSupport = enableGzipSupport;
    }


    @Override
    public String getString() {
        return mString;
    }


    @Override
    public void toServletResponse(HttpServletResponse resp) throws ResponseException {
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.setContentType(getContentType());


        try {
            OutputStream out;
            if (mEnableGzipSupport) {
                resp.setHeader(HttpHeaders.CONTENT_ENCODING, HttpHeaders.CONTENT_ENCODING_GZIP);
                out = new GZIPOutputStream(resp.getOutputStream(), true);
            } else {
                resp.setContentLength(mString.getBytes().length);
                out = resp.getOutputStream();
            }

            InputStream is = new ByteArrayInputStream(mString.getBytes("UTF-8"));
            ByteStreams.copy(is, out);
            out.flush();
            out.close();
        } catch (IOException e) {
            throw new ResponseException(e);
        }
    }


    abstract protected String getContentType();
}
