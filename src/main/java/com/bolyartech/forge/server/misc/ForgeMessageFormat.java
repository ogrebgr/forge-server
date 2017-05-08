package com.bolyartech.forge.server.misc;

import org.slf4j.helpers.MessageFormatter;


/**
 * Utility class for formatting messages
 */
public class ForgeMessageFormat {
    /**
     * Formats a message using SLF4J notation
     *
     * @param msg    Message with placeholders for values in SLF4J format
     * @param params parameters to be used to fill the placeholders
     * @return formatted message
     */
    public static String format(String msg, Object... params) {
        return MessageFormatter.format(msg, params).getMessage();
    }
}
