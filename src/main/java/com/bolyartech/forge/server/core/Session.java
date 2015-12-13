package com.bolyartech.forge.server.core;

import javax.servlet.http.HttpSession;
import java.util.Set;

/**
 * Created by ogre on 11.11.15.
 */
public interface Session {
    HttpSession raw();

    <T> T getAttribute(String name);

    void setAttribute(String name, Object value);

    Set<String> getAttributes();

    long getCreationTime();

    String getId();

    long getLastAccessedTime();

    int getMaxInactiveInterval();

    void setMaxInactiveInterval(int interval);

    void invalidate();

    boolean isNew();

    void removeAttribute(String name);
}
