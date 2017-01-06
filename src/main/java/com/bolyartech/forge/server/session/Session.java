package com.bolyartech.forge.server.session;

public interface Session {
    <T> T getVar(String varName);

    void setVar(String varName, Object value);

    int getMaxInactiveInterval();
}
