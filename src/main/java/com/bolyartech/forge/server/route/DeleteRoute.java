package com.bolyartech.forge.server.route;

import com.bolyartech.forge.server.HttpMethod;
import com.bolyartech.forge.server.handler.RouteHandler;

import javax.annotation.Nonnull;


public class DeleteRoute extends RouteImpl {
    public DeleteRoute(@Nonnull String path, @Nonnull RouteHandler routeHandler) {
        super(HttpMethod.DELETE, path, routeHandler);
    }
}
