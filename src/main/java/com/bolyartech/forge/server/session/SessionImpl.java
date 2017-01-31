package com.bolyartech.forge.server.session;

import javax.servlet.http.HttpSession;


/**
 * Session
 */
public class SessionImpl implements Session {
    private final HttpSession mHttpSession;


    /**
     * Creates new SessionImpl
     *
     * @param httpSession HTTP session
     */
    public SessionImpl(HttpSession httpSession) {
        mHttpSession = httpSession;
    }


    @Override
    public <T> T getVar(String varName) {
        //noinspection unchecked
        return (T) mHttpSession.getAttribute(varName);
    }


    @Override
    public void setVar(String varName, Object value) {
        mHttpSession.setAttribute(varName, value);
    }


    @Override
    public int getMaxInactiveInterval() {
        return mHttpSession.getMaxInactiveInterval();
    }
}