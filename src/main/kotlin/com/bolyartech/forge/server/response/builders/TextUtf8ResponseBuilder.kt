package com.bolyartech.forge.server.response.builders

import com.bolyartech.forge.server.response.TextUtf8Response

class TextUtf8ResponseBuilder(code: Int) :
    AbstractResponseBuilder(code) {

    private var body: String = ""

    fun body(body: String): TextUtf8ResponseBuilder {
        this.body = body

        return this
    }

    fun getBody(): String = body


    override fun build(): TextUtf8Response {
        return TextUtf8Response(body, getCookies(), getHeaders(), isGzipSupportEnabled(), getStatusCode())
    }
}