package com.bolyartech.forge.server.response

import com.bolyartech.forge.HttpResponseCodes
import jakarta.servlet.http.HttpServletResponse

class NotAuthorizedResponse : Response {
    companion object {
        private const val BODY = "401 Not authorized"
    }

    @Throws(ResponseException::class)
    override fun toServletResponse(resp: HttpServletResponse): Long {
        return toServletResponseHelper(resp, BODY, HttpResponseCodes.ERRC_UNAUTHORIZED)
    }
}