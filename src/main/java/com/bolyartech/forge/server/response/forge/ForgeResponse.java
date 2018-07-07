package com.bolyartech.forge.server.response.forge;

import com.bolyartech.forge.server.response.JsonResponse;

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

    private final int resultCode;


    /**
     * Creates new ForgeResponse
     *
     * @param resultCode Result code
     */
    public ForgeResponse(int resultCode) {
        super("");
        this.resultCode = resultCode;
    }


    /**
     * Creates new ForgeResponse
     *
     * @param resultCode Result code
     */
    public ForgeResponse(ForgeResponseCode resultCode) {
        super("");
        this.resultCode = resultCode.getCode();
    }


    /**
     * Creates new ForgeResponse
     *
     * @param resultCode Result code
     * @param string     Data
     */
    public ForgeResponse(int resultCode, String string) {
        super(string);
        this.resultCode = resultCode;
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
        this.resultCode = resultCode;
    }


    /**
     * Creates new ForgeResponse
     *
     * @param resultCode Result code
     * @param string     Data
     */
    public ForgeResponse(ForgeResponseCode resultCode, String string) {
        super(string);
        this.resultCode = resultCode.getCode();
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
        this.resultCode = resultCode.getCode();
    }


    @Override
    public void toServletResponse(HttpServletResponse resp) {
        resp.setHeader(FORGE_RESULT_CODE_HEADER, Integer.toString(resultCode));
        super.toServletResponse(resp);
    }


    /**
     * Returns result code
     *
     * @return Result code
     */
    public int getResultCode() {
        return resultCode;
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
