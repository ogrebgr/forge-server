package com.bolyartech.forge.server.response

import com.bolyartech.forge.HttpResponseCodes
import com.bolyartech.forge.server.response.StringResponse.Companion.MIN_SIZE_FOR_GZIP
import com.google.common.io.ByteStreams
import com.google.common.io.CountingOutputStream
import jakarta.servlet.http.Cookie
import jakarta.servlet.http.HttpServletResponse
import java.io.ByteArrayInputStream
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.util.zip.GZIPOutputStream

/**
 * Base class for specialized str responses
 */
abstract class AbstractStringResponse(
    private val str: String,
    cookiesToSet: List<Cookie> = emptyList(),
    headersToAdd: List<HttpHeader> = emptyList(),
    private val enableGzipSupport: Boolean = true,
    private val statusCode: Int = HttpResponseCodes.OK.code,
) : AbstractResponse(cookiesToSet, headersToAdd), StringResponse {

    override fun toServletResponse(resp: HttpServletResponse): Long {
        addCookiesAndHeaders(resp)
        resp.status = statusCode
        resp.contentType = getContentType()
        var cl: Long = 0
        return try {
            val ba = str.toByteArray(charset("UTF-8"))
            val out: OutputStream = if (enableGzipSupport && ba.size > MIN_SIZE_FOR_GZIP) {
                resp.setHeader(HttpHeaders.CONTENT_ENCODING, HttpHeaders.CONTENT_ENCODING_GZIP)
                CountingOutputStream(GZIPOutputStream(resp.outputStream, true))
            } else {
                resp.setContentLength(ba.size)
                resp.outputStream
            }
            val `is`: InputStream = ByteArrayInputStream(ba)
            cl = ByteStreams.copy(`is`, out)
            if (enableGzipSupport && cl > MIN_SIZE_FOR_GZIP) {
                cl = (out as CountingOutputStream).count
            }
            out.flush()
            out.close()
            cl
        } catch (e: IOException) {
            // ignore
            cl
        }
    }


    override fun getString(): String {
        return str
    }

    protected abstract fun getContentType(): String
}