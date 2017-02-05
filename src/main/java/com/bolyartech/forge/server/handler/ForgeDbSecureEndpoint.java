package com.bolyartech.forge.server.handler;

import com.bolyartech.forge.server.db.DbPool;
import com.bolyartech.forge.server.response.ResponseException;
import com.bolyartech.forge.server.response.forge.ForgeResponse;
import com.bolyartech.forge.server.route.RequestContext;

import java.sql.Connection;
import java.sql.SQLException;


/**
 * Endpoint that requires request to be using HTTPS
 */
abstract public class ForgeDbSecureEndpoint extends ForgeSecureEndpoint {
    private final DbPool mDbPool;


    /**
     * Creates new ForgeDbSecureEndpoint
     *
     * @param dbPool DB pool
     */
    public ForgeDbSecureEndpoint(DbPool dbPool) {
        mDbPool = dbPool;
    }


    /**
     * Handles HTTP request wrapped in {@link RequestContext} object and produces {@link ForgeResponse} utilizing DB
     * connection
     *
     * @param ctx Request context
     * @param dbc DB connection
     * @return Forge response
     * @throws ResponseException if there is problem handling the request
     * @throws SQLException      if there is a DB error
     */
    public abstract ForgeResponse handleForgeSecure(RequestContext ctx, Connection dbc) throws ResponseException,
            SQLException;


    @Override
    public ForgeResponse handleForgeSecure(RequestContext ctx) {
        try {
            Connection dbc = mDbPool.getConnection();
            ForgeResponse ret = handleForgeSecure(ctx, dbc);
            dbc.close();

            return ret;
        } catch (SQLException e) {
            throw new ResponseException(e);
        }
    }
}
