package com.bolyartech.forge.server.session;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


/**
 * Fake session meant to be used in unit tests
 *
 * Please note that methods {@link #getCreationTime()}, {@link #getLastAccessedTime()} and
 * {@link #getMaxInactiveInterval()} always return 0, i.e. they have empty implementation
 */
public class TestSession implements Session {
    private final String mId;
    private final Map<String, Object> mVars = new HashMap<>();


    public TestSession() {
        mId = UUID.randomUUID().toString();
    }


    public TestSession(String id) {
        mId = id;
    }


    @Override
    public String getId() {
        return null;
    }


    @Override
    public <T> T getVar(String varName) {
        //noinspection unchecked - user must take care to get same type
        return (T) mVars.get(varName);
    }


    @Override
    public void setVar(String varName, Object value) {
        mVars.put(varName, value);
    }


    @Override
    public void removeVar(String varName) {
        mVars.remove(varName);
    }


    @Override
    public int getMaxInactiveInterval() {
        return 0;
    }


    @Override
    public long getCreationTime() {
        return 0;
    }


    @Override
    public long getLastAccessedTime() {
        return 0;
    }
}
