package com.bolyartech.forge.server.route;

import com.bolyartech.forge.server.HttpMethod;
import com.bolyartech.forge.server.handler.Handler;


public class GetRoute extends RouteImpl {
    public GetRoute(String path, Handler handler) {
        super(HttpMethod.GET, path, handler);
    }
}
