package com.bolyartech.forge.server.response;

import com.google.common.base.Strings;
import com.google.common.io.ByteStreams;
import com.google.common.io.CountingOutputStream;

import javax.annotation.Nonnull;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.MessageFormat;
import java.util.zip.GZIPOutputStream;


/**
 * Response for uploading file (from server point of view)
 */
public class FileUploadResponse implements Response {
    private final File file;
    private final boolean enableGzip;


    /**
     * Creates new FileUploadResponse
     *
     * @param filePath   Path to the file which will be uploaded
     * @param enableGzip if true Gzip compression will be used if supported by the client
     */
    public FileUploadResponse(@Nonnull String filePath, boolean enableGzip) {
        if (Strings.isNullOrEmpty(filePath)) {
            throw new IllegalArgumentException("filePath null or empty");
        }

        file = new File(filePath);
        if (!file.exists()) {
            throw new IllegalArgumentException("No such file exist: " + filePath);
        }

        this.enableGzip = enableGzip;
    }


    @Override
    public long toServletResponse(@Nonnull HttpServletResponse resp) {
        resp.setContentType(HttpHeaders.CONTENT_TYPE_OCTET);
        resp.setHeader(HttpHeaders.CONTENT_DISPOSITION,
                MessageFormat.format(HttpHeaders.CONTENT_DISPOSITION_ATTACHMENT, file.getName()));

        long cl = 0L;

        InputStream is;
        try {
            is = new BufferedInputStream(new FileInputStream(file));
            OutputStream out;
            if (enableGzip && cl > Response.MIN_SIZE_FOR_GZIP) {
                resp.setHeader(HttpHeaders.CONTENT_ENCODING, HttpHeaders.CONTENT_ENCODING_GZIP);
                out = new CountingOutputStream(new GZIPOutputStream(resp.getOutputStream(), true));
            } else {
                resp.setContentLength((int) file.length());
                out = resp.getOutputStream();
                cl = file.length();
            }
            ByteStreams.copy(is, out);

            if (enableGzip && cl > Response.MIN_SIZE_FOR_GZIP) {
                cl = ((CountingOutputStream) out).getCount();
            }


            out.flush();
            out.close();

            return cl;
        } catch (IOException e) {
            throw new ResponseException(e);
        }
    }
}
