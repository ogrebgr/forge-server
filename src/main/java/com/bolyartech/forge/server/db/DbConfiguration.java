package com.bolyartech.forge.server.db;

public interface DbConfiguration {
    String getDbDsn();

    String getDbUsername();

    String getDbPassword();

    int getMaxStatements();

    int getInitialPoolSize();

    int getMinPoolSize();

    int getMaxPoolSize();

    int getIdleConnectionTestPeriod();

    boolean getTestConnectionOnCheckin();

    boolean getTestConnectionOnCheckout();
}
