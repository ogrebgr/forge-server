package com.bolyartech.forge.server.db;

public interface DbTimestampFormatter {
    String DB_TS_FORMAT = "YYYY-MM-DD hh:mm:ss";

    String format(long ts);
}
