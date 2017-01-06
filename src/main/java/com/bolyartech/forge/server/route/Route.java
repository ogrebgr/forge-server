package com.bolyartech.forge.server.route;

import com.bolyartech.forge.server.HttpMethod;
import com.bolyartech.forge.server.response.ResponseException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public interface Route {
    HttpMethod getHttpMethod();

    String getPath();

    void handle(HttpServletRequest req, HttpServletResponse response) throws ResponseException;
}
