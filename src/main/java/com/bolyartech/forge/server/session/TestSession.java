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
    private final String id;
    private final Map<String, Object> vars = new HashMap<>();


    /**
     * Creates new TestSession
     */
    public TestSession() {
        id = UUID.randomUUID().toString();
    }


    /**
     * Creates new TestSession with specific ID
     *
     * @param id ID to be used
     */
    public TestSession(String id) {
        this.id = id;
    }


    @Override
    public String getId() {
        return null;
    }


    @Override
    public <T> T getVar(String varName) {
        //noinspection unchecked - user must take care to get same type
        return (T) vars.get(varName);
    }


    @Override
    public void setVar(String varName, Object value) {
        vars.put(varName, value);
    }


    @Override
    public void removeVar(String varName) {
        vars.remove(varName);
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
