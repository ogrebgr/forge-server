package com.bolyartech.forge.server.response;

import javax.servlet.http.HttpServletResponse;


/**
 * Redirect response implementation that returns 302 Moved temporarily header
 */
public class RedirectResponseMovedTemporarily implements RedirectResponse {
    private static final String HEADER_LOCATION = "Location";
    private final String mLocation;


    /**
     * Creates new RedirectResponseMovedTemporarily
     *
     * @param location Target location
     */
    public RedirectResponseMovedTemporarily(String location) {
        mLocation = location;
    }


    @Override
    public String getLocation() {
        return mLocation;
    }


    @Override
    public void toServletResponse(HttpServletResponse resp) {
        resp.setStatus(HttpServletResponse.SC_MOVED_TEMPORARILY);
        resp.setHeader(HEADER_LOCATION, mLocation);
    }
}
