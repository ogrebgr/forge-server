package com.bolyartech.forge.server.handler;

import com.bolyartech.forge.server.db.DbPool;
import com.bolyartech.forge.server.response.ResponseException;
import com.bolyartech.forge.server.response.forge.ForgeResponse;
import com.bolyartech.forge.server.route.RequestContext;
import com.bolyartech.forge.server.session.Session;

import java.sql.Connection;
import java.sql.SQLException;


abstract public class ForgeDbEndpoint extends ForgeEndpoint implements ForgeDbEndpointInterface {
    private final DbPool mDbPool;


    public ForgeDbEndpoint(DbPool dbPool) {
        mDbPool = dbPool;
    }


    @Override
    public ForgeResponse handleForge(RequestContext ctx, Session session) throws ResponseException {
        try {
            Connection dbc = mDbPool.getConnection();
            ForgeResponse ret = handle(ctx, session, dbc);
            dbc.close();

            return ret;
        } catch (SQLException e) {
            throw new ResponseException(e);
        }
    }
}
