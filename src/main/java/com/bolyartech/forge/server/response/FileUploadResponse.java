package com.bolyartech.forge.server.response;

import com.google.common.base.Strings;
import com.google.common.io.ByteStreams;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.MessageFormat;
import java.util.zip.GZIPOutputStream;


public class FileUploadResponse implements Response {
    private final File mFile;
    private final boolean mEnableGzip;


    public FileUploadResponse(String filePath, boolean enableGzip) {
        if (Strings.isNullOrEmpty(filePath)) {
            throw new IllegalArgumentException("filePath null or empty");
        }

        mFile = new File(filePath);
        if (!mFile.exists()) {
            throw new IllegalArgumentException("No such file exist: " + filePath);
        }

        mEnableGzip = enableGzip;
    }


    @Override
    public void toServletResponse(HttpServletResponse resp) throws ResponseException {
        resp.setContentType(HttpHeaders.CONTENT_TYPE_OCTET);
        resp.setHeader(HttpHeaders.CONTENT_DISPOSITION,
                MessageFormat.format(HttpHeaders.CONTENT_DISPOSITION_ATTACHMENT, mFile.getName()));

        InputStream is;
        try {
            is = new BufferedInputStream(new FileInputStream(mFile));
            OutputStream out;
            if (mEnableGzip) {
                resp.setHeader(HttpHeaders.CONTENT_ENCODING, HttpHeaders.CONTENT_ENCODING_GZIP);
                out = new GZIPOutputStream(resp.getOutputStream(), true);
            } else {
                resp.setContentLength((int) mFile.length());
                out = resp.getOutputStream();
            }
            ByteStreams.copy(is, out);
            out.flush();
            out.close();
        } catch (IOException e) {
            throw new ResponseException(e);
        }
    }
}
