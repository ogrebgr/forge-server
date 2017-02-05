package com.bolyartech.forge.server.handler;

import com.bolyartech.forge.server.misc.TemplateEngine;
import com.bolyartech.forge.server.misc.TemplateEngineFactory;
import com.bolyartech.forge.server.response.HtmlResponse;
import com.bolyartech.forge.server.response.Response;
import com.bolyartech.forge.server.response.forge.RequiresHttpsResponse;
import com.bolyartech.forge.server.route.RequestContext;


abstract public class SecureWebPage implements RouteHandler {
    private final TemplateEngineFactory mTemplateEngineFactory;
    private final boolean mEnableGzipSupport;

    abstract public String produceHtmlSecure(RequestContext ctx, TemplateEngine tple);


    public SecureWebPage(TemplateEngineFactory templateEngineFactory, boolean enableGzipSupport) {
        mTemplateEngineFactory = templateEngineFactory;
        mEnableGzipSupport = enableGzipSupport;
    }


    public SecureWebPage(TemplateEngineFactory templateEngineFactory) {
        this(templateEngineFactory, false);
    }


    @Override
    public Response handle(RequestContext ctx) {
        if (ctx.getScheme().equals("https")) {
            String content = produceHtmlSecure(ctx, mTemplateEngineFactory.createNew());

            return new HtmlResponse(content, mEnableGzipSupport);
        } else {
            return new RequiresHttpsResponse();
        }
    }
}
