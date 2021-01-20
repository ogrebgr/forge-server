package com.bolyartech.forge.server.response;

import com.google.common.io.ByteStreams;
import com.google.common.io.CountingOutputStream;

import javax.annotation.Nonnull;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.zip.GZIPOutputStream;


/**
 * Base class for specialized string responses
 */
abstract public class AbstractStringResponse extends AbstractResponse implements StringResponse {
    private final String string;
    private final boolean enableGzipSupport;


    /**
     * Creates new AbstractStringResponse
     *
     * @param string String of the response
     */
    public AbstractStringResponse(@Nonnull String string) {
        this.string = string;
        enableGzipSupport = false;
    }


    /**
     * Creates new AbstractStringResponse
     * @param cookiesToSet list of cookies to be set
     * @param string String of the response
     */
    public AbstractStringResponse(List<Cookie> cookiesToSet, String string) {
        super(cookiesToSet);
        this.string = string;
        enableGzipSupport = false;
    }


    /**
     *
     * Creates new AbstractStringResponse
     * @param cookiesToSet list of cookies to be set
     * @param string String of the response
     * @param enableGzipSupport if true Gzip compression will be used if the client supports it
     */
    public AbstractStringResponse(List<Cookie> cookiesToSet, String string, boolean enableGzipSupport) {
        super(cookiesToSet);
        this.string = string;
        this.enableGzipSupport = enableGzipSupport;
    }


    /**
     * Creates new AbstractStringResponse
     *
     * @param string            String of the response
     * @param enableGzipSupport if true Gzip compression will be used if the client supports it
     */
    public AbstractStringResponse(@Nonnull String string, boolean enableGzipSupport) {
        this.string = string;
        this.enableGzipSupport = enableGzipSupport;
    }


    @Override
    public String getString() {
        return string;
    }


    @Override
    public long toServletResponse(@Nonnull HttpServletResponse resp) {
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.setContentType(getContentType());

        if (getCookiesToSet() != null) {
            for (Cookie c : getCookiesToSet()) {
                resp.addCookie(c);
            }
        }

        long cl = 0;

        try {
            OutputStream out;
            if (enableGzipSupport && string.getBytes().length > Response.MIN_SIZE_FOR_GZIP) {
                resp.setHeader(HttpHeaders.CONTENT_ENCODING, HttpHeaders.CONTENT_ENCODING_GZIP);
                out = new CountingOutputStream(new GZIPOutputStream(resp.getOutputStream(), true));
            } else {
                resp.setContentLength(string.getBytes().length);
                out = resp.getOutputStream();
            }

            InputStream is = new ByteArrayInputStream(string.getBytes("UTF-8"));
            cl = ByteStreams.copy(is, out);

            if (enableGzipSupport && cl > Response.MIN_SIZE_FOR_GZIP) {
                cl = ((CountingOutputStream) out).getCount();
            }

            out.flush();
            out.close();

            return cl;
        } catch (IOException e) {
            // ignore
            return cl;
        }
    }


    abstract protected String getContentType();
}
