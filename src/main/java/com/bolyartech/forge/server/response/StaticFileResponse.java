package com.bolyartech.forge.server.response;

import com.bolyartech.forge.server.misc.MimeTypeResolver;
import com.google.common.io.ByteStreams;
import com.google.common.io.CountingOutputStream;

import javax.annotation.Nonnull;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.zip.GZIPOutputStream;


/**
 * Response which uses static file as a content
 * Use this class for static HTML files, CSS or images (PNG, JPG, etc.)
 */
public class StaticFileResponse extends AbstractResponse {

    private final File file;
    private final boolean enableGzip;
    private final String mimeType;


    /**
     * Creates new StaticFileResponse
     *
     * @param mimeTypeResolver MIME type resolver (resolves the MIME type by the file extension)
     * @param file             File to be used as content
     * @param enableGzip       if true Gzip compression will be used if the client supports it
     */
    public StaticFileResponse(@Nonnull MimeTypeResolver mimeTypeResolver, @Nonnull File file, boolean enableGzip) {
        this.file = file;
        this.enableGzip = enableGzip;

        mimeType = mimeTypeResolver.resolveForFilename(this.file.getName());
    }


    /**
     * Creates new StaticFileResponse
     *
     * @param file       File to be used as content
     * @param enableGzip if true Gzip compression will be used if the client supports it
     * @param mimeType   MIME type to be used
     */
    public StaticFileResponse(@Nonnull File file, boolean enableGzip, @Nonnull String mimeType) {
        this.file = file;
        this.enableGzip = enableGzip;
        this.mimeType = mimeType;
    }


    @Override
    public long toServletResponse(@Nonnull HttpServletResponse resp) {
        long cl = 0;

        try {
            resp.setContentType(mimeType);

            ZonedDateTime ts =
                    ZonedDateTime.ofInstant(Instant.ofEpochMilli(file.lastModified()), ZoneId.of("UTC"));
            String lm = java.time.format.DateTimeFormatter.RFC_1123_DATE_TIME.format(ts);
            resp.setHeader(HttpHeaders.LAST_MODIFIED, lm);

            InputStream is = new BufferedInputStream(new FileInputStream(file));
            try {
                OutputStream out;
                if (enableGzip && file.length() > Response.MIN_SIZE_FOR_GZIP) {
                    resp.setHeader(HttpHeaders.CONTENT_ENCODING, HttpHeaders.CONTENT_ENCODING_GZIP);
                    out = new CountingOutputStream(new GZIPOutputStream(resp.getOutputStream(), true));
                } else {
                    out = resp.getOutputStream();
                    cl = file.length();
                }
                cl = ByteStreams.copy(is, out);

                if (enableGzip && cl > Response.MIN_SIZE_FOR_GZIP) {
                    cl = ((CountingOutputStream) out).getCount();
                }

                out.flush();
                out.close();

                return cl;
            } catch (IOException e) {
                // ignore
                return cl;
            }
        } catch (FileNotFoundException e) {
            // ignore
            return cl;
        }
    }


}
