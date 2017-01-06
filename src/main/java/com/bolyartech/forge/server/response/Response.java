package com.bolyartech.forge.server.response;

import javax.servlet.http.HttpServletResponse;


public interface Response {
    void toServletResponse(HttpServletResponse resp) throws ResponseException;
}
