package com.bolyartech.forge.server;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;
import com.bolyartech.forge.server.config.ForgeConfigurationException;
import com.bolyartech.forge.server.config.ForgeServerConfiguration;
import com.bolyartech.forge.server.config.ForgeServerConfigurationLoader;
import com.bolyartech.forge.server.config.ForgeServerConfigurationLoaderImpl;
import com.bolyartech.forge.server.handler.Handler;
import com.bolyartech.forge.server.module.HttpModule;
import com.bolyartech.forge.server.module.ModuleRegister;
import com.bolyartech.forge.server.module.ModuleRegisterImpl;
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


abstract public class MainServlet extends HttpServlet {
    private static final String LOGBACK_CONFIG = "conf/logback.xml";
    private static final String DEFAULT_MODULE_NAME = "default_module";
    private final org.slf4j.Logger mLogger = LoggerFactory.getLogger(this.getClass());
    private final RouteRegister mRouteRegister = new RouteRegisterImpl();
    private final ModuleRegister mModuleRegister = new ModuleRegisterImpl(mRouteRegister);


    ForgeServerConfigurationLoader mForgeServerConfigurationLoader;


    @Override
    public void init() throws ServletException {
        super.init();

        mForgeServerConfigurationLoader = new ForgeServerConfigurationLoaderImpl();
        try {
            ForgeServerConfiguration config = mForgeServerConfigurationLoader.load(this.getClass().getClassLoader());
            if (initLog(config.getServerLogName())) {
                List<HttpModule> modules = getModules();

                if (modules != null && modules.size() > 0) {
                    for (HttpModule mod : getModules()) {
                        mModuleRegister.registerModule(mod);
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


    public void addRoute(Route route) {
        mRouteRegister.register(DEFAULT_MODULE_NAME, route);
    }


    public void addRoute(HttpMethod httpMethod, String path, Handler handler) {
        mRouteRegister.register(DEFAULT_MODULE_NAME, new RouteImpl(httpMethod, path, handler));
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Route route = mModuleRegister.match(HttpMethod.GET, req.getPathInfo());

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
        Route route = mModuleRegister.match(HttpMethod.POST, req.getPathInfo());

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
        context.putProperty("application-name", serverLogName);

        try {
            jc.doConfigure(this.getClass().getClassLoader().getResourceAsStream(LOGBACK_CONFIG));
            return true;
        } catch (JoranException e) {
            mLogger.error("Cannot load logback configuration!", e);
            return false;
        }
    }
}
