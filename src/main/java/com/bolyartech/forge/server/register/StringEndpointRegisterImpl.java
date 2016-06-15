package com.bolyartech.forge.server.register;

import com.bolyartech.forge.server.StringEndpoint;
import org.slf4j.LoggerFactory;
import spark.Request;
import spark.Response;
import spark.Session;

import static spark.Spark.*;


/**
 * Used to register {@link StringEndpoint}s
 */
public class StringEndpointRegisterImpl implements StringEndpointRegister {
    private final org.slf4j.Logger mLogger = LoggerFactory.getLogger(this.getClass().getSimpleName());

    private final RootRegister mRootRegister;
    private final int mSessionTimeout;


    /**
     *
     * @param rootRegister Singleton {@link RootRegister}
     * @param sessionTimeout Session timeout, i.e. how long will be the session before seen as expired and cleared
     */
    public StringEndpointRegisterImpl(RootRegister rootRegister, int sessionTimeout) {
        mRootRegister = rootRegister;
        mSessionTimeout = sessionTimeout;
    }


    /**
     * Registers an {@link StringEndpoint}
     * @param pathPrefix Path prefix to be used. Different modules may/will use different prefixes
     * @param ep endpoint
     */
    @Override
    public void register(String pathPrefix, StringEndpoint ep) {
        mRootRegister.register(pathPrefix, ep);
        registerEndpoint(pathPrefix, ep);
    }


    /**
     * @param pathPrefix Path prefix
     * @param ep endpoint
     * @return true if enpoind is already registered
     */
    @Override
    public boolean isRegistered(String pathPrefix, StringEndpoint ep) {
        return mRootRegister.isRegistered(pathPrefix, ep);
    }


    /**
     * Actual registration of the endpoint with Spark
     * @param pathPrefix path prefix
     * @param ep enpoint
     */
    private void registerEndpoint(String pathPrefix, StringEndpoint ep) {
        final StringEndpoint sep = ep;

        switch (sep.getHttpMethod()) {
            case GET:
                get(pathPrefix + ep.getPath(), (request, response) -> handleEndpoint(sep, request, response));
                break;
            case POST:
                post(pathPrefix + ep.getPath(), (request, response) -> handleEndpoint(sep, request, response));
                break;
            case PUT:
                put(pathPrefix + ep.getPath(), (request, response) -> handleEndpoint(sep, request, response));
                break;
            case DELETE:
                delete(pathPrefix + ep.getPath(), (request, response) -> handleEndpoint(sep, request, response));
                break;
        }
    }


    /**
     * Handles HTTP request with the handler of given enpoint
     * @param sep enpoint
     * @param request Spark request object
     * @param response Spark response object
     * @return result of the handling the request
     */
    private String handleEndpoint(StringEndpoint sep, Request request, Response response) {
        initSession(request.session());
        long start = System.nanoTime();
        String ret;
        try {
            ret = sep.getHandler().handle(request, response);
            mLogger.trace("Endpoint {}:{} handler took {} nanos. ",
                    sep.getHttpMethod(),
                    sep.getPath(),
                    (System.nanoTime() - start) / 1000000f);
            return ret;
        } catch (Exception e) {
            mLogger.error("Error handling {}:{}  {}", sep.getPath(), sep.getHttpMethod(), e);
            halt(500);
            return "";
        }
    }


    /**
     * Overrides server session timeout
     * @param sess Session
     */
    protected void initSession(Session sess) {
        if (sess.isNew()) {
            sess.maxInactiveInterval(mSessionTimeout);
        }
    }
}
