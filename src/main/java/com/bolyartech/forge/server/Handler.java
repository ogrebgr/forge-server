package com.bolyartech.forge.server;

import spark.Request;
import spark.Response;

/**
 * Handler instances will be used to handle HTTP requests and will produce result ot type T.
 * @param <T>
 */
public interface Handler<T> {
    T handle(Request request, Response response) throws HandlerException;
}
