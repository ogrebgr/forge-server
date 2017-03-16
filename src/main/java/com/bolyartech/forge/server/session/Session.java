package com.bolyartech.forge.server.session;

/**
 * Session
 */
public interface Session {
    /**
     * Returns the unique session ID
     * @return session ID
     */
    String getId();

    /**
     * Returns the value of a session variable (if previously set)
     *
     * @param varName Variable naem
     * @param <T>     Type of the value (inferred)
     * @return Value of the session variable or null if not set
     */
    <T> T getVar(String varName);

    /**
     * Sets session variable
     *
     * @param varName Variable name
     * @param value   Value of the variable
     */
    void setVar(String varName, Object value);

    /**
     * Returns max inactive interval of the session.
     * If no request are made during that interval session expires
     *
     * @return max inactive interval of the session
     */
    int getMaxInactiveInterval();
}
