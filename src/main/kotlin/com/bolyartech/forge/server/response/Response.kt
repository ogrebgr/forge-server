package com.bolyartech.forge.server.response

import com.bolyartech.forge.HttpResponseCodes
import jakarta.servlet.http.HttpServletResponse
import java.io.IOException
import java.io.PrintWriter

interface Response {
    /**
     * Converts Response to HttpServletResponse
     *
     * @param resp HTTP servlet response
     * @return Length of the response
     * @throws ResponseException if there is a problem during converting
     */
    @Throws(ResponseException::class)
    fun toServletResponse(resp: HttpServletResponse): Long
}

fun toServletResponseHelper(resp: HttpServletResponse, body: String, responseHttpCode: HttpResponseCodes): Long {
    resp.status = responseHttpCode.code

    val pw: PrintWriter
    try {
        pw = resp.writer
        pw.print(body)
        pw.flush()
        pw.close()

        return body.length.toLong()
    } catch (e: IOException) {
        throw ResponseException(e)
    } catch (e: Exception) {
        throw ResponseException(e)
    }
}