package com.bolyartech.forge.server.route;

import com.bolyartech.forge.server.HttpMethod;
import com.bolyartech.forge.server.handler.Handler;


public class PostRoute extends RouteImpl {
    public PostRoute(String path, Handler handler) {
        super(HttpMethod.POST, path, handler);
    }
}
