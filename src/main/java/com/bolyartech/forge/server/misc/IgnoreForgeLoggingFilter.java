package com.bolyartech.forge.server.misc;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.filter.Filter;
import ch.qos.logback.core.spi.FilterReply;


class IgnoreForgeLoggingFilter extends Filter<ILoggingEvent> {

    @Override
    public FilterReply decide(ILoggingEvent event) {
        final String FORGE_LOGGER = "com.bolyartech.forge.server.webserverlog";

        if (event.getLoggerName() == null) {
            return FilterReply.NEUTRAL;
        } else if (event.getLoggerName().equals(FORGE_LOGGER)) {
            return FilterReply.DENY;
        } else {
            return FilterReply.NEUTRAL;
        }
    }
}