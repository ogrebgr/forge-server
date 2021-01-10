package com.bolyartech.forge.server.route;

import com.bolyartech.forge.server.HttpMethod;
import com.bolyartech.forge.server.handler.RouteHandler;
import com.bolyartech.forge.server.handler.StaticResourceNotFoundException;
import com.bolyartech.forge.server.response.Response;
import com.bolyartech.forge.server.response.ResponseException;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.ZonedDateTime;
import java.util.regex.Pattern;


/**
 * Route implementation
 */
public class RouteImpl implements Route {
    private static final Pattern PATH_PATTERN = Pattern.compile("^(/[-\\w:@&?=+,.!/~*'%$_;]*)?$");
    private final org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());
    private final org.slf4j.Logger loggerWs = LoggerFactory.getLogger("com.bolyartech.forge.server.webserverlog");
    private final HttpMethod httpMethod;
    private final String path;
    private final RouteHandler routeHandler;
    private final boolean isSupportingPathInfo;


    public RouteImpl(@Nonnull HttpMethod httpMethod, @Nonnull String path, @Nonnull RouteHandler routeHandler) {
        this(httpMethod, path, routeHandler, false);
    }


    /**
     * Creates new RouteImpl
     *
     * @param httpMethod           HTTP method
     * @param path                 Path of the route
     * @param routeHandler         Route handler
     * @param isSupportingPathInfo
     */
    public RouteImpl(@Nonnull HttpMethod httpMethod, @Nonnull String path, @Nonnull RouteHandler routeHandler, boolean isSupportingPathInfo) {
        this.isSupportingPathInfo = isSupportingPathInfo;
        if (httpMethod == null) {
            throw new NullPointerException("httpMethod is null");
        }

        if (!isValidPath(path)) {
            throw new IllegalArgumentException("Invalid path: " + path);
        }


        this.httpMethod = httpMethod;
        this.path = path;
        this.routeHandler = routeHandler;
    }


    static String normalizePath(@Nonnull String path) {
        path = path.toLowerCase();

        if (path.length() > 1) {
            if (path.endsWith("/")) {
                path = path.substring(0, path.length() - 1);
            }
        }

        return path;
    }


    @Override
    public HttpMethod getHttpMethod() {
        return httpMethod;
    }


    @Override
    public String getPath() {
        return path;
    }


    @Override
    public void handle(@Nonnull HttpServletRequest httpReq, @Nonnull HttpServletResponse httpResp) {
        String ref = "\"-\"";
        if (httpReq.getHeader("referer") != null) {
            String refRaw = httpReq.getHeader("referer");
            if (refRaw.length() > 255) {
                ref = refRaw.substring(0, 255);
            } else {
                ref = refRaw;
            }
        }

        String ua = "\"-\"";
        if (httpReq.getHeader("User-Agent") != null) {
            String uaRaw = httpReq.getHeader("User-Agent");

            if (uaRaw.length() > 255) {
                ua = uaRaw.substring(0, 255);
            } else {
                ua = uaRaw;
            }
        }

        String contentLength = "-";

        try {
            Response resp = routeHandler.handle(new RequestContextImpl(httpReq, path));
            final long cl = resp.toServletResponse(httpResp);
            if (cl > 0) {
                contentLength = Long.toString(cl);
            }

            logger.trace("{} -> {}: {} {}", httpReq.getRemoteAddr(), httpResp.getStatus(), httpMethod, httpReq.getPathInfo());

            loggerWs.trace("{} - - [{}] \"{} {}\" {} {} {} {}",
                    httpReq.getRemoteAddr(),
                    ZonedDateTime.now().format(dateTimeFormatterWebServer),
                    httpMethod,
                    httpReq.getPathInfo(),
                    httpResp.getStatus(),
                    contentLength,
                    ref,
                    ua
            );
        } catch (Exception e) {
            if (httpResp.getHeader("Content-Length") != null) {
                contentLength = httpResp.getHeader("Content-Length");
            }

            if (e instanceof StaticResourceNotFoundException) {
                logger.trace("{} -> 404: {} {}", httpReq.getRemoteAddr(), httpMethod, httpReq.getPathInfo());
                loggerWs.trace("{} - - [{}] \"{} {}\" {} {} {} {}",
                        httpReq.getRemoteAddr(),
                        ZonedDateTime.now().format(dateTimeFormatterWebServer),
                        httpMethod,
                        httpReq.getPathInfo(),
                        "404",
                        contentLength,
                        ref,
                        ua
                );
            } else {
                logger.trace("{} -> {}: {} {}", httpReq.getRemoteAddr(), httpResp.getStatus(), httpMethod, httpReq.getPathInfo());
                loggerWs.trace("{} - - [{}] \"{} {}\" {} {} {} {}",
                        httpReq.getRemoteAddr(),
                        ZonedDateTime.now().format(dateTimeFormatterWebServer),
                        httpMethod,
                        httpReq.getPathInfo(),
                        httpResp.getStatus(),
                        contentLength,
                        ref,
                        ua
                );
            }

            throw new ResponseException(e);
        }
    }


    @Override
    public boolean isSupportingPathInfo() {
        return isSupportingPathInfo;
    }


    @Override
    public String toString() {
        return getClass().getSimpleName() + " path: " + path;
    }


    private boolean isValidPath(@Nonnull String path) {
        if (path == null) {
            return false;
        }

        if (!PATH_PATTERN.matcher(path).matches()) {
            return false;
        }

        int slash2Count = countToken("//", path);
        if (slash2Count > 0) {
            return false;
        }

        int slashCount = countToken("/", path);
        int dot2Count = countToken("..", path);

        return !(dot2Count > 0 && (slashCount - slash2Count - 1) <= dot2Count);
    }


    private int countToken(@Nonnull String token, @Nonnull String target) {
        int tokenIndex = 0;
        int count = 0;
        while (tokenIndex != -1) {
            tokenIndex = target.indexOf(token, tokenIndex);
            if (tokenIndex > -1) {
                tokenIndex++;
                count++;
            }
        }
        return count;
    }
}
