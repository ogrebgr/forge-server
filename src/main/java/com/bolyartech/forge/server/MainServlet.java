package com.bolyartech.forge.server;

import com.bolyartech.forge.server.handler.RouteHandler;
import com.bolyartech.forge.server.module.HttpModule;
import com.bolyartech.forge.server.module.HttpModuleRegister;
import com.bolyartech.forge.server.module.HttpModuleRegisterImpl;
import com.bolyartech.forge.server.response.Response;
import com.bolyartech.forge.server.response.ResponseException;
import com.bolyartech.forge.server.route.*;
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
    private static final String DEFAULT_MODULE_NAME = "default_module";
    private final org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());
    private final RouteRegister routeRegister = new RouteRegisterImpl();
    private final HttpModuleRegister httpModuleRegister = new HttpModuleRegisterImpl(routeRegister);

    private final RouteHandler notFoundHandler;
    private final RouteHandler internalServerError;


    public MainServlet() {
        this.notFoundHandler = null;
        this.internalServerError = null;
    }


    public MainServlet(RouteHandler notFoundHandler, RouteHandler internalServerError) {
        this.notFoundHandler = notFoundHandler;
        this.internalServerError = internalServerError;
    }


    @Override
    public void init() throws ServletException {
        super.init();

        List<HttpModule> modules = getModules();

        if (modules != null && modules.size() > 0) {
            for (HttpModule mod : getModules()) {
                httpModuleRegister.registerModule(mod);
            }
            logger.info("Forge server initialized and started.");
        } else {
            logger.error("getModules() returned empty list of modules, so no endpoints are registered.");
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
        routeRegister.register(DEFAULT_MODULE_NAME, route);
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
        routeRegister.register(DEFAULT_MODULE_NAME, new RouteImpl(httpMethod, path, routeHandler));
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse httpResp) throws IOException {
        processRequest(req, httpResp);
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse httpResp) throws IOException {
        processRequest(req, httpResp);
    }


    protected abstract List<HttpModule> getModules();


    private void handle(HttpServletRequest req, HttpServletResponse httpResp, Route route) throws IOException {
        try {
            route.handle(req, httpResp);
        } catch (ResponseException e) {
            logger.error("Error handling {}, Error: {}", route, e.getMessage());
            logger.debug("Exception: ", e);

            if (internalServerError == null) {
                httpResp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                try {
                    PrintWriter pw = httpResp.getWriter();
                    pw.print("Internal server error");
                    pw.flush();
                    pw.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            } else {
                Response resp = internalServerError.handle(new RequestContextImpl(req, req.getPathInfo()));
                resp.toServletResponse(httpResp);
            }
        }
    }


    private void notFound(HttpServletRequest req, HttpServletResponse httpResp) throws IOException {
        logger.trace("Not found: {} {}", req.getMethod(), req.getPathInfo());
        if (notFoundHandler != null) {
            Response resp = notFoundHandler.handle(new RequestContextImpl(req, req.getPathInfo()));
            resp.toServletResponse(httpResp);
        } else {
            httpResp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            PrintWriter pw = httpResp.getWriter();
            pw.print("Not found");
            pw.flush();
            pw.close();
        }
    }


    private void processRequest(HttpServletRequest req, HttpServletResponse httpResp) throws IOException {
        Route route = httpModuleRegister.match(HttpMethod.POST, req.getPathInfo());

        if (route != null) {
            handle(req, httpResp, route);
        } else {
            notFound(req, httpResp);
        }
    }
}
