package com.bolyartech.forge.server.response;

import com.bolyartech.forge.server.misc.MimeTypeResolver;
import com.google.common.io.ByteStreams;

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
public class StaticFileResponse implements Response {

    private final MimeTypeResolver mMimeTypeResolver;
    private final File mFile;
    private final boolean mEnableGzip;


    /**
     * Creates new StaticFileResponse
     *
     * @param mimeTypeResolver MIME type resolver (resolves the MIME type by the file extension)
     * @param file             File to be used as content
     * @param enableGzip       if true Gzip compression will be used if the client supports it
     */
    public StaticFileResponse(MimeTypeResolver mimeTypeResolver, File file, boolean enableGzip) {
        mMimeTypeResolver = mimeTypeResolver;
        mFile = file;
        mEnableGzip = enableGzip;
    }


    @Override
    public void toServletResponse(HttpServletResponse resp) {
        try {
            String mimeType = mMimeTypeResolver.resolveForFilename(mFile.getName());
            resp.setContentType(mimeType);

            ZonedDateTime ts =
                    ZonedDateTime.ofInstant(Instant.ofEpochMilli(mFile.lastModified()), ZoneId.of("UTC"));
            String lm = java.time.format.DateTimeFormatter.RFC_1123_DATE_TIME.format(ts);
            resp.setHeader(HttpHeaders.LAST_MODIFIED, lm);

            InputStream is = new BufferedInputStream(new FileInputStream(mFile));
            try {
                OutputStream out;
                if (mEnableGzip) {
                    resp.setHeader(HttpHeaders.CONTENT_ENCODING, HttpHeaders.CONTENT_ENCODING_GZIP);
                    out = new GZIPOutputStream(resp.getOutputStream(), true);
                } else {
                    out = resp.getOutputStream();
                }
                ByteStreams.copy(is, out);
                out.flush();
                out.close();
            } catch (IOException e) {
                throw new ResponseException(e);
            }
        } catch (FileNotFoundException e) {
            throw new ResponseException(e);
        }
    }


}
