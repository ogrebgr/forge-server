package com.bolyartech.forge.server.session;

import javax.annotation.Nonnull;
import java.io.Serializable;
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
    public TestSession(@Nonnull String id) {
        this.id = id;
    }


    @Override
    public String getId() {
        return null;
    }


    @Override
    public <T> T getVar(@Nonnull String varName) {
        //noinspection unchecked - user must take care to get same type
        return (T) vars.get(varName);
    }


    @Override
    public <T extends Serializable> void setVar(@Nonnull String varName, @Nonnull T value) {
        vars.put(varName, value);
    }


    @Override
    public void removeVar(@Nonnull String varName) {
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
