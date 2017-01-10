package com.bolyartech.forge.server.handler;

import com.bolyartech.forge.server.misc.GzipUtils;
import com.bolyartech.forge.server.misc.MimeTypeResolver;
import com.bolyartech.forge.server.response.Response;
import com.bolyartech.forge.server.response.ResponseException;
import com.bolyartech.forge.server.response.StaticFileResponse;
import com.bolyartech.forge.server.route.RequestContext;

import java.io.File;


/**
 * Handler for static files like HTML and CSS files
 * This class uses absolute path on the file system to look for the static files
 */
public class AbsoluteStaticFileHandler implements RouteHandler {
    private final File mSourceDir;
    private final Response mNotFoundResponse;
    private final MimeTypeResolver mMimeTypeResolver;
    private final boolean mEnableGzip;


    /**
     * Creates new AbsoluteStaticFileHandler
     *
     * @param sourceDir        Source dir for the files. This must be an absolute path
     * @param notFoundResponse Not found response object which will be used if no static file matching the request is found
     * @param mimeTypeResolver MIME type resolver
     * @param enableGzip       true to enable Gzip compression (please note that client have to declare if it supports Gzip in order Gzip compression to take place)
     */
    public AbsoluteStaticFileHandler(String sourceDir, Response notFoundResponse,
                                     MimeTypeResolver mimeTypeResolver, boolean enableGzip) {
        this(new File(sourceDir), notFoundResponse, mimeTypeResolver, enableGzip);
    }


    /**
     * Creates new AbsoluteStaticFileHandler
     *
     * @param sourceDir        Source dir for the files
     * @param notFoundResponse Not found response object which will be used if no static file matching the request is found
     * @param mimeTypeResolver MIME type resolver
     * @param enableGzip       true to enable Gzip compression (please note that client have to declare if it supports Gzip in order Gzip compression to take place)
     */
    public AbsoluteStaticFileHandler(File sourceDir, Response notFoundResponse,
                                     MimeTypeResolver mimeTypeResolver, boolean enableGzip) {

        if (!sourceDir.isDirectory()) {
            throw new IllegalArgumentException("sourceDir is not a directory");
        }

        if (!sourceDir.exists()) {
            throw new IllegalArgumentException("sourceDir does not exist");
        }
        mSourceDir = sourceDir;
        mNotFoundResponse = notFoundResponse;
        mMimeTypeResolver = mimeTypeResolver;
        mEnableGzip = enableGzip;
    }


    @Override
    public Response handle(RequestContext ctx) throws ResponseException {
        File file = new File(mSourceDir, ctx.getPathInfoString());
        if (file.exists() && file.isFile()) {
            boolean actualEnableGzip = mEnableGzip && GzipUtils.supportsGzip(ctx);
            return new StaticFileResponse(mMimeTypeResolver, file, actualEnableGzip);
        } else {
            return mNotFoundResponse;
        }

    }
}
