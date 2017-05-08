package com.bolyartech.forge.server.handler;

import com.bolyartech.forge.server.misc.GzipUtils;
import com.bolyartech.forge.server.misc.MimeTypeResolver;
import com.bolyartech.forge.server.response.Response;
import com.bolyartech.forge.server.response.StaticFileResponse;
import com.bolyartech.forge.server.route.RequestContext;

import java.io.File;


/**
 * Handler for static files like HTML and CSS files
 * This class uses ClassLoader to find the static files
 *
 * @see AbsoluteStaticFileHandler
 */
public class StaticFileHandler implements RouteHandler {

    private final String mSourceDir;
    private final Response mNotFoundResponse;
    private final MimeTypeResolver mMimeTypeResolver;
    private final boolean mEnableGzip;


    /**
     * Creates StaticFileHandler
     *
     * @param sourceDir        Source directory relative to the class path
     * @param notFoundResponse Not found response object which will be used if no static file matching the request is found
     * @param mimeTypeResolver MIME type resolver
     * @param enableGzip       if true Gzip compression will be used if the client supports it
     */
    public StaticFileHandler(String sourceDir,
                             Response notFoundResponse,
                             MimeTypeResolver mimeTypeResolver,
                             boolean enableGzip) {

        mSourceDir = sourceDir;

        mNotFoundResponse = notFoundResponse;
        mEnableGzip = enableGzip;
        mMimeTypeResolver = mimeTypeResolver;
    }


    @Override
    public Response handle(RequestContext ctx) {
        File file = new File(mSourceDir + ctx.getPathInfoString());
        if (file.isFile()) {
            boolean actualEnableGzip = mEnableGzip && GzipUtils.supportsGzip(ctx);
            return new StaticFileResponse(mMimeTypeResolver, file, actualEnableGzip);
        } else {
            return mNotFoundResponse;
        }
    }
}
