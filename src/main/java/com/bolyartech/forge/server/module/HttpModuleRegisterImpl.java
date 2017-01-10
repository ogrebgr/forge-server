package com.bolyartech.forge.server.module;

import com.bolyartech.forge.server.HttpMethod;
import com.bolyartech.forge.server.route.Route;
import com.bolyartech.forge.server.route.RouteRegister;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;


/**
 * Modules register
 */
public class HttpModuleRegisterImpl implements HttpModuleRegister {
    private final RouteRegister mRouteRegister;

    private final List<HttpModule> mModules = new ArrayList<>();


    /**
     * Creates new HttpModuleRegisterImpl
     *
     * @param routeRegister Route register
     */
    public HttpModuleRegisterImpl(RouteRegister routeRegister) {
        mRouteRegister = routeRegister;
    }


    @Override
    public void registerModule(HttpModule mod) {
        if (!isModuleRegistered(mod)) {
            mModules.add(mod);
            for (Route ep : mod.createRoutes()) {
                if (!mRouteRegister.isRegistered(ep)) {
                    mRouteRegister.register(mod.getSystemName() + " (" + mod.getVersionName() + ")", ep);
                } else {
                    RouteRegister.Registration reg = mRouteRegister.getRegistration(ep);
                    throw new IllegalStateException(
                            MessageFormat.format("Path {0} already registered for module {1}",
                                    reg.mRoute.getPath(),
                                    reg.moduleName));
                }
            }
        } else {
            throw new IllegalStateException(MessageFormat.format("Module '{0}' already registered", mod.getSystemName()));
        }
    }


    @Override
    public boolean isModuleRegistered(HttpModule mod) {
        boolean ret = false;

        for (HttpModule m : mModules) {
            if (m.getSystemName().equalsIgnoreCase(mod.getSystemName())) {
                ret = true;
                break;
            }
        }

        return ret;
    }


    @Override
    public Route match(HttpMethod method, String path) {
        return mRouteRegister.match(method, path);
    }
}
