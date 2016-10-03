package com.bolyartech.forge.server.misc;

/**
 * This interface abstracts getting time for time measurements in order to provide
 * standard OS independent way of getting time in milliseconds.
 */
public interface TimeProvider {
    /**
     * Returns wall clock independent time in milliseconds.
     * @return Time in millis
     */
    long getVmTime();

    /**
     * Returns wall clock time in milliseconds
     * @return wall clock time in milliseconds
     */
    long getWallClockTime();
}
