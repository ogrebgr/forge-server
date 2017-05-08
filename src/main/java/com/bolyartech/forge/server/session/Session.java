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
     * @param varName Variable name
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
     * Removes the object bound with the specified variable name from this session. If the session does not have an
     * object bound with the specified name, this method does nothing.
     * @param varName name of the variable to be removed
     */
    void removeVar(String varName);

    /**
     * Returns max inactive interval of the session.
     * If no request are made during that interval session expires
     *
     * @return max inactive interval of the session
     */
    int getMaxInactiveInterval();


    /**
     * Returns the time when this session was created, measured in milliseconds since midnight January 1, 1970 GMT.
     * @return a long specifying when this session was created, expressed in milliseconds since 1/1/1970 GMT
     */
    long getCreationTime();

    /**
     * Returns the last time the client sent a request associated with this session, as the number of milliseconds since
     * midnight January 1, 1970 GMT, and marked by the time the container received the request.
     * <p>
     * Actions that your application takes, such as getting or setting a value associated with the session, do not
     * affect the access time.
     * @return a long representing the last time the client sent a request associated with this session, expressed in milliseconds since 1/1/1970 GMT
     */
    long getLastAccessedTime();
}
