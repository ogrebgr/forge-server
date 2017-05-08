package com.bolyartech.forge.server.handler;

import com.bolyartech.forge.server.db.DbPool;
import com.bolyartech.forge.server.response.ResponseException;
import com.bolyartech.forge.server.response.forge.ForgeResponse;
import com.bolyartech.forge.server.route.RequestContext;

import java.sql.Connection;
import java.sql.SQLException;


/**
 * Endpoint that handles a request utilizing a DB connection
 */
abstract public class ForgeDbEndpoint extends ForgeEndpoint {
    private final DbPool mDbPool;


    /**
     * Creates new ForgeDbEndpoint
     *
     * @param dbPool DB pool
     */
    public ForgeDbEndpoint(DbPool dbPool) {
        mDbPool = dbPool;
    }


    /**
     * Handles a request utilizing a DB connection
     *
     * @param ctx Request context
     * @param dbc DB connection
     * @return Forge response
     * @throws ResponseException if there is problem handling the request
     * @throws SQLException      if there is error during DB operations
     */
    @SuppressWarnings("RedundantThrows")
    abstract public ForgeResponse handleForge(RequestContext ctx, Connection dbc) throws ResponseException,
            SQLException;


    @Override
    public ForgeResponse handleForge(RequestContext ctx) {
        try {
            Connection dbc = mDbPool.getConnection();
            ForgeResponse ret = handleForge(ctx, dbc);
            dbc.close();

            return ret;
        } catch (SQLException e) {
            throw new ResponseException(e);
        }
    }
}
