package com.bolyartech.forge.server;

/**
 * Defines endpoint of the web server with HTTP method and path. Think of it as a 'webpage' but
 * instead of returning HTML different subclasses may use handlers that return different result.
 * Usually you will not use this class directly but use some of it's subclasses like {@link StringEndpoint}.
 * Please note that endpoints are distinguished by HTTP method AND path. If you define POST /hello_world
 * it will work ONLY for POST request and not for GET, i.e. if you point your browser to /hello_world (which will use GET) it
 * will give you <pre>404 Not found</pre> because there is no such endpoint defined.
 * @see StringEndpoint
 * @param <T> Type of the result that will be returned by the {@link Handler}
 */
public class Endpoint<T> {
    /**
     * HTTP method
     */
    private final HttpMethod mHttpMethod;

    /**
     * Path of the endpoint
     */
    private final String mPath;

    /**
     * Handler to be used to handle the requests
     */
    private final Handler<T> mHandler;


    /**
     *
     * @param httpMethod HTTP method like GET, POST, etc
     * @param path Path of the endpoint
     * @param handler {@link Handler} to be used to handle the requests
     */
    public Endpoint(HttpMethod httpMethod, String path, Handler<T> handler) {
        mHttpMethod = httpMethod;
        mPath = path;
        mHandler = handler;
    }


    /**
     * @return HTTP method of the endpoint
     */
    public HttpMethod getHttpMethod() {
        return mHttpMethod;
    }

    /**
     * @return Path of the endpoint
     */
    public String getPath() {
        return mPath;
    }


    /**
     * @return Handler of the enpoint
     */
    public Handler<T> getHandler() {
        return mHandler;
    }
}
