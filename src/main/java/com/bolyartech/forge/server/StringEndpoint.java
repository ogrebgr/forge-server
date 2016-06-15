package com.bolyartech.forge.server;

/**
 * Endpoint that will use Handler<String>.
 *
 * Usually this is the most used type of endpoint
 */
public class StringEndpoint extends Endpoint<String> {
    public StringEndpoint(HttpMethod httpMethod, String path, Handler<String> handler) {
        super(httpMethod, path, handler);
    }
}
