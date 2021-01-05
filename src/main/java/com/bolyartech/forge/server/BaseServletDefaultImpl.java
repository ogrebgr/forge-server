package com.bolyartech.forge.server;

import com.bolyartech.forge.server.handler.StaticResourceNotFoundException;
import com.bolyartech.forge.server.handler.RouteHandler;
import com.bolyartech.forge.server.module.HttpModule;
import com.bolyartech.forge.server.module.HttpModuleRegister;
import com.bolyartech.forge.server.module.HttpModuleRegisterImpl;
import com.bolyartech.forge.server.response.Response;
import com.bolyartech.forge.server.response.ResponseException;
import com.bolyartech.forge.server.route.*;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;


/**
 * Base servlet which is used to by the Forge server
 */
public class BaseServletDefaultImpl extends HttpServlet implements BaseServlet {
    private static final String DEFAULT_MODULE_NAME = "default_module";
    private final org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());
    private final RouteRegister routeRegister;
    private final HttpModuleRegister httpModuleRegister;

    private final List<HttpModule> modules;
    private final RouteHandler notFoundHandler;
    private final RouteHandler internalServerErrorHandler;


    public BaseServletDefaultImpl(@Nonnull List<HttpModule> modules, boolean isPathInfoEnabled, int maxPathSegments) {
        this.modules = modules;
        this.notFoundHandler = null;
        this.internalServerErrorHandler = null;
        this.routeRegister = new RouteRegisterImpl(isPathInfoEnabled, maxPathSegments);
        httpModuleRegister = new HttpModuleRegisterImpl(routeRegister);
    }


    public BaseServletDefaultImpl(@Nonnull List<HttpModule> modules,
                                  boolean isPathInfoEnabled,
                                  int maxPathSegments,
                                  @Nonnull RouteHandler notFoundHandler,
                                  @Nonnull RouteHandler internalServerErrorHandler) {

        this.modules = modules;
        this.notFoundHandler = notFoundHandler;
        this.internalServerErrorHandler = internalServerErrorHandler;
        this.routeRegister = new RouteRegisterImpl(isPathInfoEnabled, maxPathSegments);
        httpModuleRegister = new HttpModuleRegisterImpl(routeRegister);
    }


    @Override
    public void init() throws ServletException {
        super.init();

        if (modules != null && modules.size() > 0) {
            for (HttpModule mod : modules) {
                httpModuleRegister.registerModule(mod);
            }
            logger.info("Server initialized and started.");
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
    @Override
    public void addRoute(@Nonnull Route route) {
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
    @Override
    public void addRoute(@Nonnull HttpMethod httpMethod, @Nonnull String path, @Nonnull RouteHandler routeHandler) {
        routeRegister.register(DEFAULT_MODULE_NAME, new RouteImpl(httpMethod, path, routeHandler));
    }


    @Override
    protected void doGet(@Nonnull HttpServletRequest req, @Nonnull HttpServletResponse httpResp) throws IOException {
        processRequest(req, httpResp);
    }


    @Override
    protected void doPost(@Nonnull HttpServletRequest req, @Nonnull HttpServletResponse httpResp) throws IOException {
        processRequest(req, httpResp);
    }


    private void handle(@Nonnull HttpServletRequest req, @Nonnull HttpServletResponse httpResp, @Nonnull Route route) throws IOException {
        try {
            route.handle(req, httpResp);
        } catch (ResponseException e) {
            if (e.getCause() instanceof MissingParameterValue) {
                logger.warn("Missing parameter(s) in \"{}\": {}", route.getPath(), e.getCause().getMessage());
                badRequest(httpResp);
                return;
            }

            if (e.getCause() instanceof InvalidParameterValue) {
                logger.warn("Invalid parameter value in \"{}\": {}", route.getPath(), e.getCause().getMessage());
                badRequest(httpResp);
                return;
            }

            if (e.getCause() instanceof StaticResourceNotFoundException) {
                notFound(req, httpResp);
                return;
            }

            logger.error("Error handling {}, Error: {}", route, e.getMessage());
            logger.error("Exception: ", e);

            if (internalServerErrorHandler == null) {
                stockInternalServerError(httpResp);
            } else {
                try {
                    Response resp = internalServerErrorHandler.handle(new RequestContextImpl(req, req.getPathInfo()));
                    resp.toServletResponse(httpResp);
                } catch (StaticResourceNotFoundException e1) {
                    logger.warn("Your custom internalServerError threw ResourceNotFoundException: {}", e.getMessage());
                    stockInternalServerError(httpResp);
                }
            }
        }
    }


    private void notFound(@Nonnull HttpServletRequest req, @Nonnull HttpServletResponse httpResp) throws IOException {
        if (notFoundHandler != null) {
            try {
                Response resp = notFoundHandler.handle(new RequestContextImpl(req, req.getPathInfo()));
                resp.toServletResponse(httpResp);
            } catch (StaticResourceNotFoundException e) {
                logger.warn("Your custom  notFoundHandler threw ResourceNotFoundException: {}", e.getMessage());
                stockNotFound(req, httpResp);
            }

        } else {
            stockNotFound(req, httpResp);
        }
    }


    private void stockNotFound(@Nonnull HttpServletRequest req, @Nonnull HttpServletResponse httpResp) throws IOException {
        httpResp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        PrintWriter pw = httpResp.getWriter();
        pw.print("Not found");
        pw.flush();
        pw.close();
    }


    private void stockInternalServerError(@Nonnull HttpServletResponse httpResp) {
        httpResp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        try {
            PrintWriter pw = httpResp.getWriter();
            pw.print("Internal server error");
            pw.flush();
            pw.close();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }


    private void badRequest(@Nonnull HttpServletResponse httpResp) {
        httpResp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        try {
            PrintWriter pw = httpResp.getWriter();
            pw.print("Bad request");
            pw.flush();
            pw.close();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }


    private void processRequest(@Nonnull HttpServletRequest req, @Nonnull HttpServletResponse httpResp) throws IOException {
        try {
            HttpMethod method = HttpMethod.valueOf(req.getMethod());
            Route route = httpModuleRegister.match(method, req.getPathInfo());

            if (route != null) {
                handle(req, httpResp, route);
            } else {
                notFound(req, httpResp);
            }
        } catch (IllegalArgumentException e) {
            notFound(req, httpResp);
        }
    }
}
