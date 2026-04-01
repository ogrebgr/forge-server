package com.bolyartech.forge.server.response

import com.bolyartech.forge.HttpResponseCodes
import jakarta.servlet.http.HttpServletResponse

class ServiceUnavailableResponse : Response {
    companion object {
        private const val BODY = "503 Service unavailable"
    }

    @Throws(ResponseException::class)
    override fun toServletResponse(resp: HttpServletResponse): Long {
        return toServletResponseHelper(resp, BODY, HttpResponseCodes.ERRS_SERVICE_UNAVAILABLE)
    }
}