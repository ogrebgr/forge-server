package com.bolyartech.forge.server.route;

import com.bolyartech.forge.server.HttpMethod;
import com.google.common.base.CharMatcher;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class RouteRegisterImpl implements RouteRegister {
    private final org.slf4j.Logger mLogger = LoggerFactory.getLogger(this.getClass());

    private final Map<String, Registration> mEndpointsGet = new ConcurrentHashMap<>();
    private final Map<String, Registration> mEndpointsPost = new ConcurrentHashMap<>();
    private final Map<String, Registration> mEndpointsDelete = new ConcurrentHashMap<>();
    private final Map<String, Registration> mEndpointsPut = new ConcurrentHashMap<>();


    static int countSlashes(String str) {
        return CharMatcher.is('/').countIn(str);
    }


    static String removeLastPathSegment(String path) {
        return path.substring(0, path.lastIndexOf('/'));
    }


    @Override
    public void register(String moduleName, Route route) {
        if (route == null) {
            throw new NullPointerException("route is null");
        }

        switch (route.getHttpMethod()) {
            case GET:
                register(mEndpointsGet, moduleName, route);
                break;
            case POST:
                register(mEndpointsPost, moduleName, route);
                break;
            case PUT:
                register(mEndpointsPut, moduleName, route);
                break;
            case DELETE:
                register(mEndpointsDelete, moduleName, route);
                break;
        }

    }


    @Override
    public boolean isRegistered(Route ep) {
        switch (ep.getHttpMethod()) {
            case GET:
                return mEndpointsGet.containsKey(ep.getPath());
            case POST:
                return mEndpointsPost.containsKey(ep.getPath());
            case PUT:
                return mEndpointsPut.containsKey(ep.getPath());
            case DELETE:
                return mEndpointsDelete.containsKey(ep.getPath());
            default:
                return false;
        }
    }


    @Override
    public Registration getRegistration(Route ep) {
        switch (ep.getHttpMethod()) {
            case GET:
                return mEndpointsGet.get(ep.getPath());
            case POST:
                return mEndpointsPost.get(ep.getPath());
            case PUT:
                return mEndpointsPut.get(ep.getPath());
            case DELETE:
                return mEndpointsDelete.get(ep.getPath());
            default:
                return null;
        }
    }


    /**
     * If the path contains more than 15 slashes it will not be matched
     *
     * @param method
     * @param path
     * @return
     */
    @Override
    public Route match(HttpMethod method, String path) {
        path = RouteImpl.normalizePath(path);

        switch (method) {
            case GET:
                return match(mEndpointsGet, path);
            case POST:
                return match(mEndpointsPost, path);
            case PUT:
                return match(mEndpointsPut, path);
            case DELETE:
                return match(mEndpointsDelete, path);
            default:
                return null;
        }
    }


    private void register(Map<String, Registration> endpoints, String moduleName, Route route) {
        if (!endpoints.containsKey(route.getPath())) {
            endpoints.put(route.getPath(), new Registration(moduleName, route));
            mLogger.info("Registered route {} {}", route.getHttpMethod(), route.getPath());
        } else {
            throw new IllegalStateException("Registered path already exist: " + route.getPath());
        }
    }


    private Route match(Map<String, Registration> endpoints, String path) {
        Registration reg = endpoints.get(path);
        if (reg != null) {
            return reg.mRoute;
        } else {
            int count = countSlashes(path);
            // < 15 prevents DDOS attacks with intentionally maliciously composed urls that contain multiple slashes like
            // "/a/a/a/a/b/b/a/d/" in order to slow down the matching (because matching is rather expensive operation)
            if (count > 1 && count <= 15) {
                return match(endpoints, removeLastPathSegment(path));
            } else {
                return null;
            }
        }
    }
}
