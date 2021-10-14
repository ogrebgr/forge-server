package com.bolyartech.forge.server.response;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;


abstract public class AbstractResponse implements Response {
    private final List<Cookie> cookiesToSet = new ArrayList<>();
    private final List<HttpHeader> headersToAdd = new ArrayList<>();


    public AbstractResponse() {
    }


    public AbstractResponse(List<Cookie> cookiesToSet) {
        if (cookiesToSet == null) {
            throw new NullPointerException("cookiesToSet is null");
        }
        this.cookiesToSet.addAll(cookiesToSet);
    }


    public AbstractResponse(List<Cookie> cookiesToSet, List<HttpHeader> headersToAdd) {
        if (cookiesToSet == null) {
            throw new NullPointerException("cookiesToSet is null");
        }

        if (headersToAdd == null) {
            throw new NullPointerException("headersToAdd is null");
        }
        this.cookiesToSet.addAll(cookiesToSet);
        this.headersToAdd.addAll(headersToAdd);
    }


    protected void addCookiesAndHeaders(@Nonnull HttpServletResponse resp) {
        for (Cookie c : cookiesToSet) {
            resp.addCookie(c);
        }

        for (HttpHeader h : headersToAdd) {
            resp.addHeader(h.header, h.value);
        }
    }
}