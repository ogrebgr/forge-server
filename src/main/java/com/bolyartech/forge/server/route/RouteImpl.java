package com.bolyartech.forge.server.route;

import com.bolyartech.forge.server.HttpMethod;
import com.bolyartech.forge.server.handler.Handler;
import com.bolyartech.forge.server.response.Response;
import com.bolyartech.forge.server.response.ResponseException;
import com.bolyartech.forge.server.session.SessionImpl;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.regex.Pattern;


public class RouteImpl implements Route {
    private static final Pattern PATH_PATTERN = Pattern.compile("^(/[-\\w:@&?=+,.!/~*'%$_;]*)?$");
    private final org.slf4j.Logger mLogger = LoggerFactory.getLogger(this.getClass());
    private final HttpMethod mHttpMethod;
    private final String mPath;
    private final Handler mHandler;


    public RouteImpl(HttpMethod httpMethod, String path, Handler handler) {
        if (httpMethod == null) {
            throw new NullPointerException("httpMethod is null");
        }

        if (!isValidPath(path)) {
            throw new IllegalArgumentException("Invalid path: " + path);
        }

        mHttpMethod = httpMethod;
        mPath = normalizePath(path);
        mHandler = handler;
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
        return mHttpMethod;
    }


    @Override
    public String getPath() {
        return mPath;
    }


    @Override
    public void handle(HttpServletRequest httpReq, HttpServletResponse httpResp) throws ResponseException {
        try {
            mLogger.trace("Will handle {} {}", mHttpMethod, mPath);
            Response resp = mHandler.handle(new RequestContextImpl(httpReq, mPath),
                    new SessionImpl(httpReq.getSession()));
            resp.toServletResponse(httpResp);
        } catch (Exception e) {
            throw new ResponseException(e);
        }
    }


    @Override
    public String toString() {
        return getClass().getSimpleName() + " path: " + mPath;
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
