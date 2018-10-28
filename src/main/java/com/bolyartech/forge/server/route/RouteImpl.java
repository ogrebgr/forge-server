package com.bolyartech.forge.server.route;

import com.bolyartech.forge.server.HttpMethod;
import com.bolyartech.forge.server.handler.RouteHandler;
import com.bolyartech.forge.server.response.Response;
import com.bolyartech.forge.server.response.ResponseException;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.regex.Pattern;


/**
 * Route implementation
 */
public class RouteImpl implements Route {
    private static final Pattern PATH_PATTERN = Pattern.compile("^(/[-\\w:@&?=+,.!/~*'%$_;]*)?$");
    private final org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());
    private final HttpMethod httpMethod;
    private final String path;
    private final RouteHandler routeHandler;


    /**
     * Creates new RouteImpl
     *
     * @param httpMethod   HTTP method
     * @param path         Path of the route
     * @param routeHandler Route handler
     */
    public RouteImpl(HttpMethod httpMethod, String path, RouteHandler routeHandler) {
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


    static String normalizePath(String path) {
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
    public void handle(HttpServletRequest httpReq, HttpServletResponse httpResp) {
        try {
            logger.trace("{} {} IP: {}", httpMethod, path, httpReq.getRemoteAddr());
            Response resp = routeHandler.handle(new RequestContextImpl(httpReq, path));
            resp.toServletResponse(httpResp);
        } catch (Exception e) {
            throw new ResponseException(e);
        }
    }


    @Override
    public String toString() {
        return getClass().getSimpleName() + " path: " + path;
    }


    private boolean isValidPath(String path) {
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


    private int countToken(String token, String target) {
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
