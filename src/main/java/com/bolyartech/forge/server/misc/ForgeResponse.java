package com.bolyartech.forge.server.misc;

public class ForgeResponse {
    private final int mCode;
    private final String mPayload;


    public ForgeResponse(int code, String payload) {
        mCode = code;

        if (payload == null) {
            mPayload = "";
        } else {
            mPayload = payload;
        }
    }


    public int getCode() {
        return mCode;
    }


    public String getPayload() {
        return mPayload;
    }
}
