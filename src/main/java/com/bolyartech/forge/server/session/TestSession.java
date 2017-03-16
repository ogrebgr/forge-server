package com.bolyartech.forge.server.session;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


/**
 * Fake session meant to be used in unit tests
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
    public int getMaxInactiveInterval() {
        return 0;
    }
}
