package com.bolyartech.forge.server.response;

import com.bolyartech.forge.server.misc.MimeTypeResolver;
import com.google.common.io.ByteStreams;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.zip.GZIPOutputStream;


public class StaticFileResponse implements Response {

    private final MimeTypeResolver mMimeTypeResolver;
    private final File mFile;
    private final boolean mEnableGzip;


    public StaticFileResponse(MimeTypeResolver mimeTypeResolver, File file, boolean enableGzip) {
        mMimeTypeResolver = mimeTypeResolver;
        mFile = file;
        mEnableGzip = enableGzip;
    }


    @Override
    public void toServletResponse(HttpServletResponse resp) throws ResponseException {
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
