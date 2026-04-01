package com.bolyartech.forge.server.response

import com.bolyartech.forge.HttpResponseCodes
import jakarta.servlet.http.Cookie

open class TextUtf8Response(
    str: String,
    cookiesToSet: List<Cookie> = emptyList(),
    headersToAdd: List<HttpHeader> = emptyList(),
    enableGzipSupport: Boolean = true,
    statusCode: Int = HttpResponseCodes.OK.code,
) : AbstractStringResponse(str, cookiesToSet, headersToAdd, enableGzipSupport, statusCode) {
    companion object {
        private const val CONTENT_TYPE = "text/plain;charset=UTF-8"
    }

    override fun getContentType(): String {
        return CONTENT_TYPE
    }
}