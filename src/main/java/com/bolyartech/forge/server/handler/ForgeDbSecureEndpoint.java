package com.bolyartech.forge.server.handler;

import com.bolyartech.forge.server.db.DbPool;
import com.bolyartech.forge.server.response.ResponseException;
import com.bolyartech.forge.server.response.forge.ForgeResponse;
import com.bolyartech.forge.server.route.RequestContext;
import com.bolyartech.forge.server.session.Session;

import java.sql.Connection;
import java.sql.SQLException;


abstract public class ForgeDbSecureEndpoint extends ForgeSecureEndpoint implements ForgeDbSecureEndpointInterface {
    private final DbPool mDbPool;


    public ForgeDbSecureEndpoint(DbPool dbPool) {
        mDbPool = dbPool;
    }


    @Override
    public ForgeResponse handleSecure(RequestContext ctx, Session session) throws ResponseException {
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
