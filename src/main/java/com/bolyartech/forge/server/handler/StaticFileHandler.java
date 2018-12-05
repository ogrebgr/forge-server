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

    private final String sourceDir;
    private final MimeTypeResolver mimeTypeResolver;
    private final boolean enableGzip;


    /**
     * Creates StaticFileHandler
     *
     * @param sourceDir        Source directory relative to the class path
     * @param mimeTypeResolver MIME type resolver
     * @param enableGzip       if true Gzip compression will be used if the client supports it
     */
    public StaticFileHandler(String sourceDir,
                             MimeTypeResolver mimeTypeResolver,
                             boolean enableGzip) {

        this.sourceDir = sourceDir;

        this.enableGzip = enableGzip;
        this.mimeTypeResolver = mimeTypeResolver;
    }


    @Override
    public Response handle(RequestContext ctx) throws ResourceNotFoundException {
        File file = new File(sourceDir + ctx.getPathInfoString());
        if (file.isFile()) {
            boolean actualEnableGzip = enableGzip && GzipUtils.supportsGzip(ctx);
            return new StaticFileResponse(mimeTypeResolver, file, actualEnableGzip);
        } else {
            throw new ResourceNotFoundException("Cannot find file " + ctx.getPathInfoString());
        }
    }
}
