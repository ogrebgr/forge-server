package com.bolyartech.forge.server.session;

import javax.servlet.http.HttpSession;


/**
 * Session
 */
public class SessionImpl implements Session {
    private final HttpSession httpSession;


    /**
     * Creates new SessionImpl
     *
     * @param httpSession HTTP session
     */
    public SessionImpl(HttpSession httpSession) {
        this.httpSession = httpSession;
    }


    @Override
    public String getId() {
        return httpSession.getId();
    }


    @Override
    public <T> T getVar(String varName) {
        //noinspection unchecked
        return (T) httpSession.getAttribute(varName);
    }


    @Override
    public void setVar(String varName, Object value) {
        httpSession.setAttribute(varName, value);
    }


    @Override
    public void removeVar(String varName) {
        httpSession.removeAttribute(varName);
    }


    @Override
    public int getMaxInactiveInterval() {
        return httpSession.getMaxInactiveInterval();
    }


    @Override
    public long getCreationTime() {
        return httpSession.getCreationTime();
    }


    @Override
    public long getLastAccessedTime() {
        return httpSession.getLastAccessedTime();
    }
}
