package com.bolyartech.forge.server.handler;

import com.bolyartech.forge.server.db.DbPool;
import com.bolyartech.forge.server.misc.TemplateEngineFactory;
import com.bolyartech.forge.server.response.HtmlResponse;
import com.bolyartech.forge.server.response.Response;
import com.bolyartech.forge.server.response.ResponseException;
import com.bolyartech.forge.server.route.RequestContext;

import java.sql.Connection;
import java.sql.SQLException;


abstract public class DbWebPage implements DbWebPageInterface {
    private final TemplateEngineFactory mTemplateEngineFactory;
    private final boolean mEnableGzipSupport;
    private final DbPool mDbPool;


    public DbWebPage(TemplateEngineFactory templateEngineFactory, boolean enableGzipSupport, DbPool dbPool) {
        mTemplateEngineFactory = templateEngineFactory;
        mEnableGzipSupport = enableGzipSupport;
        mDbPool = dbPool;
    }


    public DbWebPage(TemplateEngineFactory templateEngineFactory, DbPool dbPool) {
        this(templateEngineFactory, false, dbPool);
    }


    @Override
    public Response handle(RequestContext ctx) throws ResponseException {
        try {
            Connection dbc = mDbPool.getConnection();
            String content = produceHtml(ctx, mTemplateEngineFactory.createNew(), dbc);
            dbc.close();

            return new HtmlResponse(content, mEnableGzipSupport);
        } catch (SQLException e) {
            throw new ResponseException(e);
        }
    }
}
