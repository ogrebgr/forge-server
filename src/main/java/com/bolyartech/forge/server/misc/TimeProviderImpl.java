package com.bolyartech.forge.server.misc;

public class TimeProviderImpl implements TimeProvider {

    /**
     * Returns wall clock independent time in milliseconds.
     * Based on System.nanoTime().
     * @return Time in millis
     */
    @Override
    public long getVmTime() {
        return System.nanoTime() / 1000000;
    }


    @Override
    public long getWallClockTime() {
        return System.currentTimeMillis();
    }
}
