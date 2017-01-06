package com.bolyartech.forge.server.handler;

import com.bolyartech.forge.server.misc.TemplateEngine;
import com.bolyartech.forge.server.route.RequestContext;
import com.bolyartech.forge.server.session.Session;


public interface WebPageInterface extends Handler {
    String produceHtml(RequestContext ctx, Session session, TemplateEngine tple);
}
