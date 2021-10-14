package com.bolyartech.forge.server.response;

import jakarta.servlet.http.HttpServletResponse;

import javax.annotation.Nonnull;


/**
 * Redirect response implementation that returns 302 Moved temporarily header
 */
public class RedirectResponseMovedTemporarily implements RedirectResponse {
    private static final String HEADER_LOCATION = "Location";
    private final String location;


    /**
     * Creates new RedirectResponseMovedTemporarily
     *
     * @param location Target location
     */
    public RedirectResponseMovedTemporarily(@Nonnull String location) {
        this.location = location;
    }


    @Override
    public String getLocation() {
        return location;
    }


    @Override
    public long toServletResponse(@Nonnull HttpServletResponse resp) {
        resp.setStatus(HttpServletResponse.SC_MOVED_TEMPORARILY);
        resp.setHeader(HEADER_LOCATION, location);

        return 0;
    }
}
