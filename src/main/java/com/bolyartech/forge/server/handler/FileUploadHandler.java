package com.bolyartech.forge.server.handler;

import com.bolyartech.forge.server.misc.GzipUtils;
import com.bolyartech.forge.server.response.FileUploadResponse;
import com.bolyartech.forge.server.response.Response;
import com.bolyartech.forge.server.route.RequestContext;


/**
 * Handler for file uploads (from server point of view)
 * Use this handler when user needs to download (from his point of view) a file.
 */
abstract public class FileUploadHandler implements RouteHandler {
    private final boolean enableGzip;


    /**
     * Creates new FileUploadHandler
     *
     * @param enableGzip if true Gzip compression will be used if the client supports it
     */
    public FileUploadHandler(boolean enableGzip) {
        this.enableGzip = enableGzip;
    }


    @Override
    public Response handle(RequestContext ctx) {
        boolean actualEnableGzip = enableGzip && GzipUtils.supportsGzip(ctx);
        return handleFileUpload(ctx, actualEnableGzip);
    }


    /**
     * Handles file upload
     * <p>
     * Example:
     * <pre>
     * <code>
     * public FileUploadResponse handleFileUpload(RequestContext ctx, boolean actualEnableGzip) {
     *    return new FileUploadResponse("/home/user/some_file.txt", actualEnableGzip);
     * }
     * </code>
     * </pre>
     *
     * @param ctx              Request context
     * @param actualEnableGzip true if Gzip compression is requested AND available
     * @return File upload response
     */
    abstract FileUploadResponse handleFileUpload(RequestContext ctx, boolean actualEnableGzip);
}
