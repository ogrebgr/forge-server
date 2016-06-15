package com.bolyartech.forge.server.config;

public interface DbConfiguration {
    String getDbDsn();
    String getDbUsername();
    String getDbPassword();

    int getMaxStatements();
    int getInitalPoolSize();
    int getMinPoolSize();
    int getMaxPoolSize();
    int getIdleConnectionTestPeriod();
    boolean getTestConnectionOnCheckin();
    boolean getTestConnectionOnCheckout();
}
