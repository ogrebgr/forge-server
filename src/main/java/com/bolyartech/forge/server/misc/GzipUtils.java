package com.bolyartech.forge.server.misc;

import com.bolyartech.forge.server.response.HttpHeaders;
import com.bolyartech.forge.server.route.RequestContext;

import java.util.List;


/**
 * Utility class for Gzip
 */
public class GzipUtils {
    private GzipUtils() {
        throw new AssertionError("Non-instantiable utility class");
    }


    /**
     * Checks if the client supports Gzip compression
     * Checks if the header 'Accept-Encoding' contains 'gzip'
     *
     * @param ctx Request context
     * @return true if client supports Gzip encoding
     */
    public static boolean supportsGzip(RequestContext ctx) {
        List<String> values = ctx.getHeaderValues(HttpHeaders.ACCEPT_ENCODING);
        if (values != null) {
            for (String val : values) {
                if (val.contains(",")) {
                    String[] exploded = val.split(",");
                    for (String s : exploded) {
                        if (s.contains(HttpHeaders.CONTENT_ENCODING_GZIP)) {
                            return true;
                        }
                    }
                } else {
                    if (val.contains(HttpHeaders.CONTENT_ENCODING_GZIP)) {
                        return true;
                    }
                }
            }

            return false;
        } else {
            return false;
        }
    }
}
