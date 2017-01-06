package com.bolyartech.forge.server.response;

import javax.servlet.http.HttpServletResponse;


public class RedirectResponseImpl implements RedirectResponse {
    private static final String HEADER_LOCATION = "Location";
    private final String mLocation;


    public RedirectResponseImpl(String location) {
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
