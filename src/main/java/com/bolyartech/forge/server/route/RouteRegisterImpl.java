package com.bolyartech.forge.server.route;

import com.bolyartech.forge.server.HttpMethod;
import com.google.common.base.CharMatcher;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * Route register
 */
public class RouteRegisterImpl implements RouteRegister {
    private final org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());

    private final Map<String, Registration> endpointsStatics = new ConcurrentHashMap<>();
    private final Map<String, Registration> endpointsGet = new ConcurrentHashMap<>();
    private final Map<String, Registration> endpointsPost = new ConcurrentHashMap<>();
    private final Map<String, Registration> endpointsDelete = new ConcurrentHashMap<>();
    private final Map<String, Registration> endpointsPut = new ConcurrentHashMap<>();

    private final boolean isPathInfoEnabled;
    private final int maxPathSegments;


    public RouteRegisterImpl(boolean isPathInfoEnabled, int maxPathSegments) {
        this.isPathInfoEnabled = isPathInfoEnabled;
        this.maxPathSegments = maxPathSegments;
    }


    static int countSlashes(String str) {
        return CharMatcher.is('/').countIn(str);
    }


    static String removeLastPathSegment(@Nonnull String path) {
        if (path.endsWith("/")) {
            return path.substring(0, path.lastIndexOf('/'));
        } else {
            return path.substring(0, path.lastIndexOf('/') + 1);
        }
    }


    @Override
    public void register(@Nonnull String moduleName, @Nonnull Route route) {
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
    }


    @Override
    public void registerStatics(String moduleSystemName, Route route) {
        register(endpointsStatics, moduleSystemName, route);
    }


    @Override
    public boolean isRegistered(@Nonnull Route route) {
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
    public boolean isRegisteredStatics(@Nonnull Route route) {
        return endpointsStatics.containsKey(route.getPath());
    }


    @Override
    public Registration getRegistration(@Nonnull Route route) {
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


    @Override
    public Registration getRegistrationStatics(@Nonnull Route route) {
        return endpointsStatics.get(route.getPath());
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
    public Route match(@Nonnull HttpMethod method, @Nonnull String path) {
        path = RouteImpl.normalizePath(path);

        switch (method) {
            case GET:
                Route tmp = match(endpointsStatics, path, false);

                if (tmp == null) {
                    tmp = match(endpointsGet, path, false);
                }

                return tmp;
            case POST:
                return match(endpointsPost, path, false);
            case PUT:
                return match(endpointsPut, path, false);
            case DELETE:
                return match(endpointsDelete, path, false);
            default:
                return null;
        }
    }


    private void register(@Nonnull Map<String, Registration> endpoints, @Nonnull String moduleName, Route route) {
        if (!endpoints.containsKey(route.getPath())) {
            endpoints.put(route.getPath(), new Registration(moduleName, route));
            logger.info("Registered route {} {}", route.getHttpMethod(), route.getPath());
        } else {
            throw new IllegalStateException("Registered path already exist: " + route.getPath());
        }
    }


    private Route match(@Nonnull Map<String, Registration> endpoints, @Nonnull String path, boolean pathInfoMode) {
        Registration reg = endpoints.get(path);
        if (reg != null) {
            Route tmp = reg.mRoute;
            if (pathInfoMode) {
                if (tmp.isSupportingPathInfo()) {
                    return tmp;
                } else {
                    return null;
                }
            } else {
                return tmp;
            }
        } else {
            if (isPathInfoEnabled) {
                // maxPathSegments prevents DDOS attacks with intentionally maliciously composed urls that contain multiple slashes like
                // "/a/a/a/a/b/b/a/d/" in order to slow down the matching (because matching is rather expensive operation)
                if (maxPathSegments == 0) {
                    return match(endpoints, removeLastPathSegment(path), true);
                } else {
                    int count = countSlashes(path);
                    if (count >= 1 && count <= maxPathSegments) {
                        return match(endpoints, removeLastPathSegment(path), true);
                    } else {
                        return null;
                    }
                }
            } else {
                return null;
            }
        }
    }
}
