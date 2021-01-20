package com.bolyartech.forge.server.response;

import javax.servlet.http.Cookie;
import java.util.List;


abstract public class AbstractResponse implements Response {
    private final List<Cookie> cookiesToSet;

    public AbstractResponse() {
        this(null);
    }

    public AbstractResponse(List<Cookie> cookiesToSet) {
        this.cookiesToSet = cookiesToSet;
    }

    protected List<Cookie> getCookiesToSet() {
        return cookiesToSet;
    }
}
