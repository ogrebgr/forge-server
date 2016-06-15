package com.bolyartech.forge.server.handlers.db;

import com.bolyartech.forge.server.misc.ForgeResponse;
import spark.Request;
import spark.Response;

import java.sql.Connection;
import java.sql.SQLException;


public interface DbHandler {
    ForgeResponse handleWithDb(Request request, Response response, Connection dbc) throws SQLException;
}
