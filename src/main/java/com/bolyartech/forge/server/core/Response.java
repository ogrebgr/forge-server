package com.bolyartech.forge.server.core;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by ogre on 11.11.15.
 */
public interface Response {
    void status(int statusCode);

    void type(String contentType);

    void body(String body);

    String body();

    HttpServletResponse raw();

    void redirect(String location);

    void redirect(String location, int httpStatusCode);

    void header(String header, String value);

    void cookie(String name, String value);

    void cookie(String name, String value, int maxAge);

    void cookie(String name, String value, int maxAge, boolean secured);

    void cookie(String name, String value, int maxAge, boolean secured, boolean httpOnly);

    void cookie(String path, String name, String value, int maxAge, boolean secured);

    void cookie(String path, String name, String value, int maxAge, boolean secured, boolean httpOnly);

    void removeCookie(String name);
}
