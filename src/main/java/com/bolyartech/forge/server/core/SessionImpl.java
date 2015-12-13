/*
 * Copyright (C) 2012-2015 Ognyan Bankov
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.bolyartech.forge.server.core;

import javax.servlet.http.HttpSession;
import java.util.Enumeration;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by ogre on 11.11.15.
 */
public class SessionImpl implements Session {
    private final HttpSession mHttpSession;


    public SessionImpl(HttpSession mHttpSession) {
        if (mHttpSession == null) {
            throw new IllegalArgumentException("session cannot be null");
        }
        this.mHttpSession = mHttpSession;
    }


    @Override
    public HttpSession raw() {
        return mHttpSession;
    }


    /**
     * Returns the object bound with the specified name in this session, or null if no object is bound under the name.
     *
     * @param name a string specifying the name of the object
     * @param <T>  The type parameter
     * @return the object with the specified name
     */
    @SuppressWarnings("unchecked")
    public <T> T getAttribute(String name) {
        return (T) mHttpSession.getAttribute(name);
    }

    /**
     * Binds an object to this session, using the name specified.
     *
     * @param name  the name to which the object is bound; cannot be null
     * @param value the object to be bound
     */
    public void setAttribute(String name, Object value) {
        mHttpSession.setAttribute(name, value);
    }

    /**
     * @return an <code>Enumeration</code> of <code>String</code> objects
     * containing the names of all the objects bound to this mHttpSession.
     */
    public Set<String> getAttributes() {
        TreeSet<String> attributes = new TreeSet<String>();
        Enumeration<String> enumeration = mHttpSession.getAttributeNames();
        while (enumeration.hasMoreElements()) {
            attributes.add(enumeration.nextElement());
        }
        return attributes;
    }

    /**
     * @return the time when this session was created, measured in milliseconds since midnight January 1, 1970 GMT.
     */
    public long getCreationTime() {
        return mHttpSession.getCreationTime();
    }

    /**
     * @return a string containing the unique identifier assigned to this mHttpSession.
     */
    public String getId() {
        return mHttpSession.getId();
    }

    /**
     * @return the last time the client sent a request associated with this session,
     * as the number of milliseconds since midnight January 1, 1970 GMT, and marked
     * by the time the container received the request.
     */
    public long getLastAccessedTime() {
        return mHttpSession.getLastAccessedTime();
    }

    /**
     * @return the maximum time interval, in seconds, that the container
     * will keep this session open between client accesses.
     */
    public int getMaxInactiveInterval() {
        return mHttpSession.getMaxInactiveInterval();
    }

    /**
     * Specifies the time, in seconds, between client requests the web container will invalidate this mHttpSession.
     *
     * @param interval the interval
     */
    public void setMaxInactiveInterval(int interval) {
        mHttpSession.setMaxInactiveInterval(interval);
    }

    /**
     * Invalidates this session then unbinds any objects bound to it.
     */
    public void invalidate() {
        mHttpSession.invalidate();
    }

    /**
     * @return true if the client does not yet know about the session or if the client chooses not to join the mHttpSession.
     */
    public boolean isNew() {
        return mHttpSession.isNew();
    }

    /**
     * Removes the object bound with the specified name from this mHttpSession.
     *
     * @param name the name of the object to remove from this session
     */
    public void removeAttribute(String name) {
        mHttpSession.removeAttribute(name);
    }
}
