package com.bolyartech.forge.server.module;

import com.google.common.base.Strings;

abstract public class AbstractForgeModule implements ForgeModule {
    private final String mSitePathPrefix;
    private String mModulePathPrefix = "";


    /**
     *
     * @param sitePathPrefix Must end with '/'
     * @param modulePathPrefix  Must end with '/' if non-empty string
     */
    public AbstractForgeModule(String sitePathPrefix, String modulePathPrefix) {
        if (Strings.isNullOrEmpty(sitePathPrefix)) {
            throw new IllegalArgumentException("sitePathPrefix is null or empty");
        }

        if (!isValidModulePathPrefix(modulePathPrefix)) {
            throw new IllegalArgumentException("Invalid modulePathPrefix: " + modulePathPrefix);
        }


        mSitePathPrefix = sitePathPrefix;
        mModulePathPrefix = modulePathPrefix;
    }


    public String getSitePathPrefix() {
        return mSitePathPrefix;
    }


    public String getModulePathPrefix() {
        return mModulePathPrefix;
    }


    private boolean isValidModulePathPrefix(String prefix) {
        if (prefix != null) {
            if (prefix.equals("")) {
                return true;
            } else {
                return prefix.endsWith("/");
            }
        } else {
            return false;
        }
    }
}
