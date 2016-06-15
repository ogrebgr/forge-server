package com.bolyartech.forge.server.handlers;

import com.bolyartech.forge.server.Handler;
import com.bolyartech.forge.server.HandlerException;
import com.bolyartech.forge.server.misc.ForgeResponse;
import spark.Request;
import spark.Response;

abstract public class ForgeHandler implements Handler<String> {
    public static final String FORGE_RESULT_CODE_HEADER = "X-Forge-Result-Code";
    abstract protected ForgeResponse handleForge(Request request, Response response) throws HandlerException;

    @Override
    public String handle(Request request, Response response) throws HandlerException {
        ForgeResponse resp = handleForge(request, response);
        response.header(FORGE_RESULT_CODE_HEADER, Integer.toString(resp.getCode()));
        return resp.getPayload();
    }
}