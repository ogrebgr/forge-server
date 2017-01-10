package com.bolyartech.forge.server.response.forge;

import com.bolyartech.forge.server.response.JsonResponse;
import com.bolyartech.forge.server.response.ResponseException;

import javax.servlet.http.HttpServletResponse;


/**
 * Forge response
 * <p>
 * Forge responses are used by the Forge framework. They provide response result code in a 'X-Forge-Result-Code'
 * header and the result payload/data (if any) in the response body (usually as JSON).
 * <p>
 * Positive result codes indicate successful handling of the request.
 * Negative result codes indicate that there is some error handling the request.
 *
 * @see BasicResponseCodes
 */
public class ForgeResponse extends JsonResponse {
    private static final String FORGE_RESULT_CODE_HEADER = "X-Forge-Result-Code";

    private final int mResultCode;


    /**
     * Creates new ForgeResponse
     *
     * @param resultCode Result code
     */
    public ForgeResponse(int resultCode) {
        super("");
        mResultCode = resultCode;
    }


    /**
     * Creates new ForgeResponse
     *
     * @param resultCode Result code
     */
    public ForgeResponse(ForgeResponseCode resultCode) {
        super("");
        mResultCode = resultCode.getCode();
    }


    /**
     * Creates new ForgeResponse
     *
     * @param resultCode Result code
     * @param string     Data
     */
    public ForgeResponse(int resultCode, String string) {
        super(string);
        mResultCode = resultCode;
    }


    /**
     * Creates new ForgeResponse
     *
     * @param resultCode        Result code
     * @param string            Data
     * @param enableGzipSupport if true Gzip compression will be used if the client supports it
     */
    public ForgeResponse(int resultCode, String string, boolean enableGzipSupport) {
        super(string, enableGzipSupport);
        mResultCode = resultCode;
    }


    /**
     * Creates new ForgeResponse
     *
     * @param resultCode Result code
     * @param string     Data
     */
    public ForgeResponse(ForgeResponseCode resultCode, String string) {
        super(string);
        mResultCode = resultCode.getCode();
    }


    /**
     * Creates new ForgeResponse
     *
     * @param resultCode        Result code
     * @param string            Data
     * @param enableGzipSupport if true Gzip compression will be used if the client supports it
     */
    public ForgeResponse(ForgeResponseCode resultCode, String string, boolean enableGzipSupport) {
        super(string, enableGzipSupport);
        mResultCode = resultCode.getCode();
    }


    @Override
    public void toServletResponse(HttpServletResponse resp) throws ResponseException {
        resp.setHeader(FORGE_RESULT_CODE_HEADER, Integer.toString(mResultCode));
        super.toServletResponse(resp);
    }


    /**
     * Returns result code
     *
     * @return Result code
     */
    public int getResultCode() {
        return mResultCode;
    }


    /**
     * Returns the payload
     *
     * @return Payload
     */
    public String getPayload() {
        return getString();
    }
}
