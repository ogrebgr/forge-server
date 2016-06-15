package com.bolyartech.forge.server.handlers.db;

import com.bolyartech.forge.server.HandlerException;
import com.bolyartech.forge.server.db.DbPool;
import com.bolyartech.forge.server.handlers.ForgeSecureHandler;
import com.bolyartech.forge.server.misc.ForgeResponse;
import org.slf4j.LoggerFactory;
import spark.Request;
import spark.Response;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.MessageFormat;

abstract public class SecureDbHandler extends ForgeSecureHandler implements DbHandler {
    private final org.slf4j.Logger mLogger = LoggerFactory.getLogger(this.getClass().getSimpleName());
    private final DbPool mDbPool;


    public SecureDbHandler(DbPool dbPool) {
        mDbPool = dbPool;
    }


    @Override
    protected ForgeResponse handleForgeSecure(Request request, Response response) throws HandlerException {
        try {
            Connection dbc = mDbPool.getConnection();
            ForgeResponse ret = handleWithDb(request, response, dbc);
            dbc.close();

            return ret;
        } catch (SQLException e) {
            throw new HandlerException(MessageFormat.format("Error in handleSecure() with DB: {0}", e));
        }
    }
}