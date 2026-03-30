package com.bolyartech.forge.server.response.builders

import jakarta.servlet.http.HttpServletResponse

class OkHtmlResponseBuilder(private val body: String) : HtmlResponseBuilder(HttpServletResponse.SC_OK) {
    init {
        super.body(body)
    }
}