package com.bolyartech.forge.server.module;

import com.google.common.base.Strings;

abstract public class AbstractForgeModule implements ForgeModule {
    private String mModulePathPrefix = "";


    /**
     *
     * @param sitePathPrefix Must end with '/'
     * @param modulePathPrefix  Must end with '/' if non-empty string
     */
    public AbstractForgeModule(String modulePathPrefix) {
        if (!isValidModulePathPrefix(modulePathPrefix)) {
            throw new IllegalArgumentException("Invalid modulePathPrefix: " + modulePathPrefix);
        }


        mModulePathPrefix = modulePathPrefix;
    }


    public String getModulePathPrefix() {
        return mModulePathPrefix;
    }


    private boolean isValidModulePathPrefix(String prefix) {
        //noinspection SimplifiableIfStatement
        if (prefix != null) {
            return prefix.equals("") || prefix.endsWith("/");
        } else {
            return false;
        }
    }
}
