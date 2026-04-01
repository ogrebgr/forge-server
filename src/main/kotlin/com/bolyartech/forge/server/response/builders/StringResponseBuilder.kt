package com.bolyartech.forge.server.response.builders

import com.bolyartech.forge.server.response.HtmlResponse

@Deprecated("This class is wrongly using HtmlResponse which contains content type text/html", ReplaceWith("TextUtf9ResponseBuilder"))
open class StringResponseBuilder(code: Int) :
    AbstractResponseBuilder(code) {

    private var body: String = ""

    fun body(body: String): StringResponseBuilder {
        this.body = body

        return this
    }

    fun getBody(): String = body


    override fun build(): HtmlResponse {
        return HtmlResponse(body, getCookies(), getHeaders(), isGzipSupportEnabled(), getStatusCode())
    }
}