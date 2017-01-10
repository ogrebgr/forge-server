package com.bolyartech.forge.server.db;

import com.mchange.v2.c3p0.AbstractConnectionCustomizer;

import java.sql.Connection;


/**
 * C3p0 connection customizer which set the transaction isolation level to
 * <code>Connection.TRANSACTION_READ_COMMITTED</code>
 */
public class C3p0ConnectionCustomizer extends AbstractConnectionCustomizer {
    @Override
    public void onAcquire(Connection c, String parentDataSourceIdentityToken) throws Exception {
        super.onAcquire(c, parentDataSourceIdentityToken);
        c.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
    }
}
