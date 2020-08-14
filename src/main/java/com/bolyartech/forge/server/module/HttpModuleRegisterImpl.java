package com.bolyartech.forge.server.module;

import com.bolyartech.forge.server.HttpMethod;
import com.bolyartech.forge.server.route.GetRouteStatics;
import com.bolyartech.forge.server.route.Route;
import com.bolyartech.forge.server.route.RouteRegister;

import javax.annotation.Nonnull;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;


/**
 * Modules register
 */
public class HttpModuleRegisterImpl implements HttpModuleRegister {
    private final RouteRegister routeRegister;

    private final List<HttpModule> modules = new ArrayList<>();


    /**
     * Creates new HttpModuleRegisterImpl
     *
     * @param routeRegister Route register
     */
    public HttpModuleRegisterImpl(@Nonnull RouteRegister routeRegister) {
        this.routeRegister = routeRegister;
    }


    @Override
    public void registerModule(@Nonnull HttpModule mod) {
        if (!isModuleRegistered(mod)) {
            modules.add(mod);
            for (Route ep : mod.createRoutes()) {
                if (ep instanceof GetRouteStatics) {
                    if (!routeRegister.isRegisteredStatics(ep)) {
                        routeRegister.registerStatics(mod.getSystemName() + " (" + mod.getVersionName() + ")", ep);
                    } else {
                        RouteRegister.Registration reg = routeRegister.getRegistrationStatics(ep);
                        throw new IllegalStateException(
                                MessageFormat.format("Path {0} already registered in statics for module {1}",
                                        reg.mRoute.getPath(),
                                        reg.moduleName));
                    }
                } else {
                    if (!routeRegister.isRegistered(ep)) {
                        routeRegister.register(mod.getSystemName() + " (" + mod.getVersionName() + ")", ep);
                    } else {
                        RouteRegister.Registration reg = routeRegister.getRegistration(ep);
                        throw new IllegalStateException(
                                MessageFormat.format("Path {0} already registered for module {1}",
                                        reg.mRoute.getPath(),
                                        reg.moduleName));
                    }
                }
            }
        } else {
            throw new IllegalStateException(MessageFormat.format("Module '{0}' already registered", mod.getSystemName()));
        }
    }


    @Override
    public boolean isModuleRegistered(@Nonnull HttpModule mod) {
        boolean ret = false;

        for (HttpModule m : modules) {
            if (m.getSystemName().equalsIgnoreCase(mod.getSystemName())) {
                ret = true;
                break;
            }
        }

        return ret;
    }


    @Override
    public Route match(@Nonnull HttpMethod method, @Nonnull String path) {
        return routeRegister.match(method, path);
    }
}
