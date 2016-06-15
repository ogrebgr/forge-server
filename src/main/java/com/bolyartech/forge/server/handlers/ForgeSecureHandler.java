package com.bolyartech.forge.server.handlers;

import com.bolyartech.forge.server.Handler;
import com.bolyartech.forge.server.HandlerException;
import com.bolyartech.forge.server.handlers.ForgeHandler;
import com.bolyartech.forge.server.misc.ForgeResponse;
import org.slf4j.LoggerFactory;
import spark.Request;
import spark.Response;

abstract public class ForgeSecureHandler implements Handler<String> {
    private final org.slf4j.Logger mLogger = LoggerFactory.getLogger(this.getClass().getSimpleName());

    protected abstract ForgeResponse handleForgeSecure(Request request, Response response) throws HandlerException;


    @Override
    public String handle(Request request, Response response) throws HandlerException {
        String scheme = request.scheme().toLowerCase();

        if (scheme.equals("https")) {
            ForgeResponse resp = handleForgeSecure(request, response);
            response.header(ForgeHandler.FORGE_RESULT_CODE_HEADER, Integer.toString(resp.getCode()));
            return resp.getPayload();
        } else {
            mLogger.warn("Attempt to access secure page {} via http", request.servletPath());
            return null;
        }
    }
}
