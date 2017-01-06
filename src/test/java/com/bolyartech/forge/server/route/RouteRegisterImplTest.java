package com.bolyartech.forge.server.route;

import org.junit.Test;

import static org.junit.Assert.assertTrue;


public class RouteRegisterImplTest {
    @Test
    public void testRemoveLastPathSegment() {
        String source = "/presni/chudesni";
        String result = RouteRegisterImpl.removeLastPathSegment(source);

        assertTrue("Last segment not removed. result: " + result, result.equals("/presni"));
    }


    @Test
    public void testCountSlashes() {
        String source = "/presni/chudesni";
        int count = RouteRegisterImpl.countSlashes(source);
        assertTrue("Invalid count: " + count, count == 2);
    }
}
