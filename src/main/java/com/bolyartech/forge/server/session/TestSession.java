package com.bolyartech.forge.server.session;

import java.util.HashMap;
import java.util.Map;


public class TestSession implements Session {
    private final Map<String, Object> mVars = new HashMap<>();


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
