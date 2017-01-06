package com.bolyartech.forge.server.misc;

import com.google.common.base.Strings;


public class Params {
    /**
     * Non-instantiable utility class
     */
    private Params() {
        throw new AssertionError();
    }


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
