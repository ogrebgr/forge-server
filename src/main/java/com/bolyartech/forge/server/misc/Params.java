package com.bolyartech.forge.server.misc;

import com.google.common.base.Strings;


/**
 * Utility class for GET/POST/PI parameters
 */
public class Params {
    /**
     * Non-instantiable utility class
     */
    private Params() {
        throw new AssertionError();
    }


    /**
     * Checks if all strings are non-null and non-empty
     *
     * @param pars Strings to be checked
     * @return true if all strings are non-null and non-empty
     */
    public static boolean areAllPresent(String... pars) {
        boolean ret = true;

        if (pars == null || pars.length == 0) {
            throw new IllegalArgumentException("pars is empty");
        }

        for (String par : pars) {
            if (Strings.isNullOrEmpty(par)) {
                ret = false;
                break;
            }
        }

        return ret;
    }

}
