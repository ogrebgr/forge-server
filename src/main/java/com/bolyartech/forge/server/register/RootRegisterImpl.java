package com.bolyartech.forge.server.register;

import com.bolyartech.forge.server.Endpoint;
import com.bolyartech.forge.server.HttpMethod;
import javafx.util.Pair;
import org.slf4j.LoggerFactory;

import java.text.MessageFormat;
import java.util.*;

/**
 * Root register for a give site. This class must be singleton and fed to all other
 * registers (like {@link StringEndpointRegister}) in order to avoid duplicate registrations
 * for same HTTP method and path
 */
public class RootRegisterImpl implements RootRegister {
    private final org.slf4j.Logger mLogger = LoggerFactory.getLogger(this.getClass().getSimpleName());

    private Map<String, List<HttpMethod>> mEndpoints = new HashMap<>();


    @Override
    public void register(String pathPrefix, Endpoint ep) {
        mLogger.info("Registering endpoint: " + ep.getHttpMethod() + " " + pathPrefix + ep.getPath());
        List<HttpMethod> list = mEndpoints.get(pathPrefix + ep.getPath());
        if (list != null) {
            if (!list.contains(ep.getHttpMethod())) {
                list.add(ep.getHttpMethod());
            } else {
                throw new IllegalStateException(MessageFormat.format("Endpoint {0}/{1} is already registered",
                        ep.getPath(),
                        ep.getHttpMethod()));
            }
        } else {
            List<HttpMethod> methods = new ArrayList<>();
            methods.add(ep.getHttpMethod());
            mEndpoints.put(ep.getPath(), methods);
        }
    }


    @Override
    public boolean isRegistered(String pathPrefix, Endpoint ep) {
        List<HttpMethod> list = mEndpoints.get(ep.getPath());
        return list != null && list.contains(ep.getHttpMethod());
    }


    @Override
    public List<Pair<String, List<HttpMethod>>> getRegisteredEndpoints() {
        List<Pair<String, List<HttpMethod>>> ret = new ArrayList<>();
        for (String key : mEndpoints.keySet()) {
            List<HttpMethod> tmp = mEndpoints.get(key);

            ret.add(new Pair<>(key, Collections.unmodifiableList(tmp)));
        }

        return ret;
    }
}
