package com.bolyartech.forge.server.core;

import spark.QueryParamsMap;
import spark.Session;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Set;

/**
 * Created by ogre on 11.11.15.
 */
public interface Request {
    Map<String, String> params();

    String params(String param);

    String[] splat();

    String requestMethod();

    String scheme();

    String host();

    String userAgent();

    int port();

    String pathInfo();

    String servletPath();

    String contextPath();

    String url();

    String contentType();

    String ip();

    String body();

    byte[] bodyAsBytes();

    int contentLength();

    String queryParams(String queryParam);

    String[] queryParamsValues(String queryParam);

    String headers(String header);

    Set<String> queryParams();

    Set<String> headers();

    String queryString();

    void attribute(String attribute, Object value);

    <T> T attribute(String attribute);

    Set<String> attributes();

    HttpServletRequest raw();

    QueryParamsMap queryMap();

    QueryParamsMap queryMap(String key);

    Session session();

    Session session(boolean create);

    Map<String, String> cookies();

    String cookie(String name);

    String uri();

    String protocol();
}
