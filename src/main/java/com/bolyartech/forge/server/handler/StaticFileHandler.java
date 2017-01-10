package com.bolyartech.forge.server.handler;

import com.bolyartech.forge.server.misc.GzipUtils;
import com.bolyartech.forge.server.misc.MimeTypeResolver;
import com.bolyartech.forge.server.response.Response;
import com.bolyartech.forge.server.response.ResponseException;
import com.bolyartech.forge.server.response.StaticFileResponse;
import com.bolyartech.forge.server.route.RequestContext;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;


/**
 * Handler for static files like HTML and CSS files
 * This class uses ClassLoader to find the static files
 *
 * @see AbsoluteStaticFileHandler
 */
public class StaticFileHandler implements RouteHandler {

    private final ClassLoader mClassLoader;
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
    public StaticFileHandler(String sourceDir, Response notFoundResponse, MimeTypeResolver mimeTypeResolver,
                             boolean enableGzip) {
        this(sourceDir, notFoundResponse, mimeTypeResolver, enableGzip, null);
    }


    /**
     * Creates StaticFileHandler
     *
     * @param sourceDir        Source directory relative to the class path
     * @param notFoundResponse Not found response object which will be used if no static file matching the request is found
     * @param mimeTypeResolver MIME type resolver
     * @param enableGzip       if true Gzip compression will be used if the client supports it
     * @param classLoader      Class loader to be used to find the static files on the class path
     */
    public StaticFileHandler(String sourceDir,
                             Response notFoundResponse,
                             MimeTypeResolver mimeTypeResolver,
                             boolean enableGzip,
                             ClassLoader classLoader) {

        if (sourceDir.startsWith("/")) {
            mSourceDir = sourceDir.substring(1);
        } else {
            mSourceDir = sourceDir;
        }

        if (classLoader != null) {
            mClassLoader = classLoader;
        } else {
            mClassLoader = this.getClass().getClassLoader();
        }
        mNotFoundResponse = notFoundResponse;
        mEnableGzip = enableGzip;
        mMimeTypeResolver = mimeTypeResolver;
    }


    @Override
    public Response handle(RequestContext ctx) throws ResponseException {
        String filePath = mSourceDir + ctx.getPathInfoString();

        URL url = mClassLoader.getResource(filePath);
        if (url != null) {
            try {
                File file = new File(url.toURI());
                if (file.isFile()) {
                    boolean actualEnableGzip = mEnableGzip && GzipUtils.supportsGzip(ctx);
                    return new StaticFileResponse(mMimeTypeResolver, file, actualEnableGzip);
                } else {
                    return mNotFoundResponse;
                }
            } catch (URISyntaxException e) {
                throw new ResponseException(e);
            }
        } else {
            return mNotFoundResponse;
        }
    }
}
