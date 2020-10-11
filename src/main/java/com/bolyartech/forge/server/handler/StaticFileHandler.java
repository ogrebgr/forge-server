package com.bolyartech.forge.server.handler;

import com.bolyartech.forge.server.misc.GzipUtils;
import com.bolyartech.forge.server.misc.MimeTypeResolver;
import com.bolyartech.forge.server.response.Response;
import com.bolyartech.forge.server.response.StaticFileResponse;
import com.bolyartech.forge.server.route.RequestContext;

import javax.annotation.Nonnull;
import java.io.File;
import java.util.ArrayList;
import java.util.List;


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
    private final List<String> directoryIndexes = new ArrayList<>();


    /**
     * Creates StaticFileHandler
     *
     * @param sourceDir        Source directory relative to the class path
     * @param mimeTypeResolver MIME type resolver
     * @param enableGzip       if true Gzip compression will be used if the client supports it
     */
    public StaticFileHandler(@Nonnull String sourceDir,
                             @Nonnull MimeTypeResolver mimeTypeResolver,
                             boolean enableGzip) {

        this.sourceDir = sourceDir;

        this.enableGzip = enableGzip;
        this.mimeTypeResolver = mimeTypeResolver;
    }


    /**
     * @param sourceDir               Source directory relative to the class path
     * @param mimeTypeResolver        MIME type resolver
     * @param enableGzip              if true Gzip compression will be used if the client supports it
     * @param directoryIndexFilenames Comma separated list of directory index file names, i.e. index.html,index.htm
     */
    public StaticFileHandler(@Nonnull String sourceDir,
                             @Nonnull MimeTypeResolver mimeTypeResolver,
                             boolean enableGzip,
                             String directoryIndexFilenames) {

        this.sourceDir = sourceDir;

        this.enableGzip = enableGzip;
        this.mimeTypeResolver = mimeTypeResolver;

        String[] filenames = directoryIndexFilenames.split(",");
        for (String f : filenames) {
            if (f.contains(File.separator)) {
                throw new IllegalArgumentException("directoryIndexFilenames items cannot contain path separator: " + f);
            }

            directoryIndexes.add(f);
        }
    }


    @Override
    public Response handle(@Nonnull RequestContext ctx) throws StaticResourceNotFoundException {
        File file = new File(sourceDir + ctx.getPathInfoString());
        if (file.exists() && file.isFile()) {
            boolean actualEnableGzip = enableGzip && GzipUtils.supportsGzip(ctx);
            return new StaticFileResponse(mimeTypeResolver, file, actualEnableGzip);
        } else {
            if (file.isDirectory() && directoryIndexes.size() > 0) {
                for (String f : directoryIndexes) {
                    File tmp = new File(file, f);
                    if (tmp.exists() && tmp.isFile()) {
                        boolean actualEnableGzip = enableGzip && GzipUtils.supportsGzip(ctx);
                        return new StaticFileResponse(mimeTypeResolver, tmp, actualEnableGzip);
                    }
                }
            }

            throw new StaticResourceNotFoundException("Cannot find file " + ctx.getPathInfoString());
        }
    }
}
