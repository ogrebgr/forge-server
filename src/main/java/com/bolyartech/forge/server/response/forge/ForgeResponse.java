package com.bolyartech.forge.server.response.forge;

import com.bolyartech.forge.server.response.JsonResponse;
import com.bolyartech.forge.server.response.ResponseException;

import javax.servlet.http.HttpServletResponse;


public class ForgeResponse extends JsonResponse {
    private static final String FORGE_RESULT_CODE_HEADER = "X-Forge-Result-Code";

    private final int mResultCode;


    public ForgeResponse(int resultCode) {
        super("");
        mResultCode = resultCode;
    }


    public ForgeResponse(ForgeResponseCode resultCode) {
        super("");
        mResultCode = resultCode.getCode();
    }


    public ForgeResponse(int resultCode, String string) {
        super(string);
        mResultCode = resultCode;
    }


    public ForgeResponse(int resultCode, String string, boolean enableGzipSupport) {
        super(string, enableGzipSupport);
        mResultCode = resultCode;
    }


    public ForgeResponse(ForgeResponseCode resultCode, String string) {
        super(string);
        mResultCode = resultCode.getCode();
    }


    public ForgeResponse(ForgeResponseCode resultCode, String string, boolean enableGzipSupport) {
        super(string, enableGzipSupport);
        mResultCode = resultCode.getCode();
    }


    @Override
    public void toServletResponse(HttpServletResponse resp) throws ResponseException {
        resp.setHeader(FORGE_RESULT_CODE_HEADER, Integer.toString(mResultCode));
        super.toServletResponse(resp);
    }


    public int getResultCode() {
        return mResultCode;
    }


    public String getPayload() {
        return getString();
    }
}
