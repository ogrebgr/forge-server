package com.bolyartech.forge.server.route;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertTrue;


public class RequestContextImplTest {
    @Test
    public void testExtractParameters() {
        Map<String, String> mGetParams = new HashMap<>();

        RequestContextImpl.extractParameters("fd=%D0%BF&dd=%D0%B9%D0%BA%D0%B9", mGetParams);

        assertTrue("", mGetParams.get("fd").equals("п"));
    }
}
