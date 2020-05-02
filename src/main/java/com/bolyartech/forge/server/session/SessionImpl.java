package com.bolyartech.forge.server.session;

import javax.annotation.Nonnull;
import javax.servlet.http.HttpSession;
import java.io.Serializable;


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
    public SessionImpl(@Nonnull HttpSession httpSession) {
        this.httpSession = httpSession;
    }


    @Override
    public String getId() {
        return httpSession.getId();
    }


    @Override
    public <T> T getVar(@Nonnull String varName) {
        //noinspection unchecked
        return (T) httpSession.getAttribute(varName);
    }


    @Override
    public <T extends Serializable> void setVar(@Nonnull String varName, @Nonnull T value) {
        httpSession.setAttribute(varName, value);
    }


    @Override
    public void removeVar(@Nonnull String varName) {
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
