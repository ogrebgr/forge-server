package com.bolyartech.forge.server;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;
import com.bolyartech.forge.server.config.FileForgeServerConfigurationLoader;
import com.bolyartech.forge.server.config.ForgeConfigurationException;
import com.bolyartech.forge.server.config.ForgeServerConfiguration;
import com.bolyartech.forge.server.config.ForgeServerConfigurationLoader;
import com.bolyartech.forge.server.handler.RouteHandler;
import com.bolyartech.forge.server.module.HttpModule;
import com.bolyartech.forge.server.module.HttpModuleRegister;
import com.bolyartech.forge.server.module.HttpModuleRegisterImpl;
import com.bolyartech.forge.server.response.ResponseException;
import com.bolyartech.forge.server.route.Route;
import com.bolyartech.forge.server.route.RouteImpl;
import com.bolyartech.forge.server.route.RouteRegister;
import com.bolyartech.forge.server.route.RouteRegisterImpl;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;


/**
 * Main (and only) servlet which is used to by the Forge server
 * This class initializes all the modules (and respectively routes). Users must inherit it and implement
 * {@link #getModules()} in order to define their sites
 */
abstract public class MainServlet extends HttpServlet {
    private static final String LOGBACK_CONFIG = "conf/logback.xml";
    private static final String DEFAULT_MODULE_NAME = "default_module";
    private final org.slf4j.Logger mLogger = LoggerFactory.getLogger(this.getClass());
    private final RouteRegister mRouteRegister = new RouteRegisterImpl();
    private final HttpModuleRegister mHttpModuleRegister = new HttpModuleRegisterImpl(mRouteRegister);


    ForgeServerConfigurationLoader mForgeServerConfigurationLoader;


    @Override
    public void init() throws ServletException {
        super.init();

        mForgeServerConfigurationLoader = new FileForgeServerConfigurationLoader();
        try {
            ForgeServerConfiguration config = mForgeServerConfigurationLoader.load();
            if (initLog(config.getServerLogName())) {
                List<HttpModule> modules = getModules();

                if (modules != null && modules.size() > 0) {
                    for (HttpModule mod : getModules()) {
                        mHttpModuleRegister.registerModule(mod);
                    }
                    mLogger.info("Forge server initialized and started.");
                } else {
                    mLogger.error("getModules() returned empty list of modules, so no endpoints are registered.");
                }
            } else {
                mLogger.error("Server not initialized properly and it is not functional.");
            }

        } catch (ForgeConfigurationException e) {
            mLogger.error("Server not initialized properly and it is not functional.");
        }
    }


    /**
     * Adds a route to the "default" module
     * <p>
     * If you are creating just a small site and don't want to bother with creating modules use this method.
     *
     * @param route Route to be added
     */
    public void addRoute(Route route) {
        mRouteRegister.register(DEFAULT_MODULE_NAME, route);
    }


    /**
     * Adds a route
     *
     * @param httpMethod   HTTP method for the route
     * @param path         Path of the route
     * @param routeHandler RouteHandler of the route
     * @see #addRoute(Route)
     */
    public void addRoute(HttpMethod httpMethod, String path, RouteHandler routeHandler) {
        mRouteRegister.register(DEFAULT_MODULE_NAME, new RouteImpl(httpMethod, path, routeHandler));
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Route route = mHttpModuleRegister.match(HttpMethod.GET, req.getPathInfo());

        if (route != null) {
            handle(req, resp, route);
        } else {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            PrintWriter pw = resp.getWriter();
            pw.print("Not found");
            pw.flush();
            pw.close();
        }
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Route route = mHttpModuleRegister.match(HttpMethod.POST, req.getPathInfo());

        if (route != null) {
            handle(req, resp, route);
        } else {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            PrintWriter pw = resp.getWriter();
            pw.print("Not found");
            pw.flush();
            pw.close();
        }
    }


    protected abstract List<HttpModule> getModules();


    private void handle(HttpServletRequest req, HttpServletResponse resp, Route route) {
        try {
            route.handle(req, resp);
        } catch (ResponseException e) {
            mLogger.error("Error handling {}, Error: {}", route, e.getMessage());
            mLogger.debug("Exception: ", e);
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            try {
                PrintWriter pw = resp.getWriter();
                pw.print("Internal server error");
                pw.flush();
                pw.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }


    private boolean initLog(String serverLogName) {
        LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();
        JoranConfigurator jc = new JoranConfigurator();
        jc.setContext(context);
        context.reset();
        context.putProperty("server-name", serverLogName);

        try {
            jc.doConfigure(this.getClass().getClassLoader().getResourceAsStream(LOGBACK_CONFIG));
            return true;
        } catch (JoranException e) {
            mLogger.error("Cannot load logback configuration!", e);
            return false;
        }
    }
}
