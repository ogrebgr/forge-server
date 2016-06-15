package com.bolyartech.forge.server.register;

import com.bolyartech.forge.server.Endpoint;

public interface EndpointRegister<T extends Endpoint> {
    void register(String pathPrefix, T ep);
    boolean isRegistered(String pathPrefix, T ep);
}
