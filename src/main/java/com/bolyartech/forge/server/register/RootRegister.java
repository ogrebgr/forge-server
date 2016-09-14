package com.bolyartech.forge.server.register;

import com.bolyartech.forge.server.Endpoint;
import com.bolyartech.forge.server.HttpMethod;
import javafx.util.Pair;

import java.util.List;

public interface RootRegister {
    void register(String pathPrefix, Endpoint<?> ep);
    @SuppressWarnings("UnusedParameters")
    boolean isRegistered(String pathPrefix, Endpoint<?> ep);
    List<Pair<String, List<HttpMethod>>> getRegisteredEndpoints();
}
