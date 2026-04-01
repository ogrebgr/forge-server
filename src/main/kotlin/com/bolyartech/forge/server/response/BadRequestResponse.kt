package com.bolyartech.forge.server.response

import com.bolyartech.forge.HttpResponseCodes
import jakarta.servlet.http.HttpServletResponse

class BadRequestResponse : Response {
    companion object {
        private const val BODY = "400 Bad request"
    }

    @Throws(ResponseException::class)
    override fun toServletResponse(resp: HttpServletResponse): Long {
        return toServletResponseHelper(resp, BODY, HttpResponseCodes.ERRC_BAD_REQUEST)
    }
}