package com.bolyartech.forge.server.route;

import com.bolyartech.forge.server.HttpMethod;
import com.google.common.base.CharMatcher;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * Route register
 */
public class RouteRegisterImpl implements RouteRegister {
    private final org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());

    private final Map<String, Registration> endpointsGet = new ConcurrentHashMap<>();
    private final Map<String, Registration> endpointsPost = new ConcurrentHashMap<>();
    private final Map<String, Registration> endpointsDelete = new ConcurrentHashMap<>();
    private final Map<String, Registration> endpointsPut = new ConcurrentHashMap<>();

    private int maxPathSegments = 0;

    static int countSlashes(String str) {
        return CharMatcher.is('/').countIn(str);
    }


    static String removeLastPathSegment(String path) {
        if (path.endsWith("/")) {
            return path.substring(0, path.lastIndexOf('/'));
        } else {
            return path.substring(0, path.lastIndexOf('/') + 1);
        }
    }


    @Override
    public void register(String moduleName, Route route) {
        if (route == null) {
            throw new NullPointerException("route is null");
        }

        switch (route.getHttpMethod()) {
            case GET:
                register(endpointsGet, moduleName, route);
                break;
            case POST:
                register(endpointsPost, moduleName, route);
                break;
            case PUT:
                register(endpointsPut, moduleName, route);
                break;
            case DELETE:
                register(endpointsDelete, moduleName, route);
                break;
        }

        maxPathSegments = Math.max(maxPathSegments, countSlashes(route.getPath()));
    }


    @Override
    public boolean isRegistered(Route route) {
        switch (route.getHttpMethod()) {
            case GET:
                return endpointsGet.containsKey(route.getPath());
            case POST:
                return endpointsPost.containsKey(route.getPath());
            case PUT:
                return endpointsPut.containsKey(route.getPath());
            case DELETE:
                return endpointsDelete.containsKey(route.getPath());
            default:
                return false;
        }
    }


    @Override
    public Registration getRegistration(Route route) {
        switch (route.getHttpMethod()) {
            case GET:
                return endpointsGet.get(route.getPath());
            case POST:
                return endpointsPost.get(route.getPath());
            case PUT:
                return endpointsPut.get(route.getPath());
            case DELETE:
                return endpointsDelete.get(route.getPath());
            default:
                return null;
        }
    }


    /**
     * Matches Route against HTTP method and URL path
     * If the path contains more than 15 slashes it will not be matched
     *
     * @param method HTTP method
     * @param path   URL Path
     * @return matched route or null if no route is matched
     */
    @Override
    public Route match(HttpMethod method, String path) {
        path = RouteImpl.normalizePath(path);

        switch (method) {
            case GET:
                return match(endpointsGet, path);
            case POST:
                return match(endpointsPost, path);
            case PUT:
                return match(endpointsPut, path);
            case DELETE:
                return match(endpointsDelete, path);
            default:
                return null;
        }
    }


    private void register(Map<String, Registration> endpoints, String moduleName, Route route) {
        if (!endpoints.containsKey(route.getPath())) {
            endpoints.put(route.getPath(), new Registration(moduleName, route));
            logger.info("Registered route {} {}", route.getHttpMethod(), route.getPath());
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
            // maxPathSegments prevents DDOS attacks with intentionally maliciously composed urls that contain multiple slashes like
            // "/a/a/a/a/b/b/a/d/" in order to slow down the matching (because matching is rather expensive operation)
            if (count > 1 && count <= maxPathSegments) {
                return match(endpoints, removeLastPathSegment(path));
            } else {
                return null;
            }
        }
    }
}
