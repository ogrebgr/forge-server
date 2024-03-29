package com.bolyartech.forge.server.route;

import com.bolyartech.forge.server.HttpMethod;
import com.bolyartech.forge.server.session.Session;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.Part;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.util.List;
import java.util.Map;


/**
 * Request context is a wrapper around all data of a HTTP request (including session)
 */
public interface RequestContext {
    /**
     * Returns current session.
     * Implementation may decide to initialize the session lazily when this method is called in order to prevent overhead
     *
     * @return Session
     */
    Session getSession();

    /**
     * Returns the value of a query parameter as a String, or null if the parameter does not exist.
     *
     * @param parameterName Parameter name
     * @return Parameter value
     * @deprecated use {@link #getFromQuery(String)}
     */
    String getFromGet(@Nonnull String parameterName);

    /**
     * Returns the value of a query parameter as a String, or null if the parameter does not exist. Alias of {@link #getFromGet(String)}
     *
     * @param parameterName Parameter name
     * @return Parameter value
     */
    String getFromQuery(@Nonnull String parameterName);

    /**
     * Returns multi value of a query parameter as a List<String>, or null if the parameter does not exist.
     * Multi-values happen for example when several checkboxes with same name and different values are present in the form
     *
     * @param parameterName Parameter name
     * @return List with values (if any)
     */
    List<String> getMultipleFromQuery(@Nonnull String parameterName);

    /**
     * Returns the value of a POST parameter as a String, or null if the parameter does not exist.
     *
     * @param parameterName Parameter name
     * @return Parameter value
     */
    String getFromPost(@Nonnull String parameterName);

    /**
     * Returns multi value of a query parameter as a List<String>, or null if the parameter does not exist.
     * Multi-values happen for example when several checkboxes with same name and different values are present in the form
     *
     * @param parameterName Parameter name
     * @return List with values (if any)
     */
    List<String> getMultipleFromPost(@Nonnull String parameterName);

    /**
     * @return List containing path info parameters ordered from left to right
     */
    List<String> getPathInfoParameters();

    /**
     * Alias of {@link #getPathInfoParameters()}
     *
     * @return List containing path info parameters ordered from left to right
     */
    List<String> getPi();

    /**
     * Returns the path of the matched route
     *
     * @return path of the matched route
     */
    String getRoutePath();

    /**
     * Returns HTTP scheme, i.e. http or https (in lowercase)
     *
     * @return HTTP scheme, i.e. http or https (in lowercase)
     */
    String getScheme();

    /**
     * For multipart request this method returns a request part
     *
     * @param partName Part name
     * @return request part
     * @throws IOException      if there is problem retrieving the content
     * @throws ServletException if there is problem retrieving the content
     */
    Part getPart(@Nonnull String partName) throws IOException, ServletException;

    /**
     * Returns the Path info string, i.e. the path part after the route path
     * For example if we have a request URL <code>http://somedomain.com/route/path/path/info/string</code> and
     * route path '/route/path/path' the returned string will be  /path/info/string
     *
     * @return String containing the path info
     */
    String getPathInfoString();

    /**
     * Returns Cookie
     *
     * @param cookieName Cookie name
     * @return Cookie
     */
    Cookie getCookie(@Nonnull String cookieName);

    /**
     * Optionally gets from query parameters if parameter is present
     *
     * @param parameterName Parameter name
     * @param defaultValue  Default value to be returned if parameter is not present
     * @return Parameter value or the default value if not present
     */
    String optFromGet(@Nonnull String parameterName, @Nonnull String defaultValue);

    /**
     * Optionally gets from query parameters if parameter is present. Alias of {@link #optFromGet(String, String)}
     *
     * @param parameterName Parameter name
     * @param defaultValue  Default value to be returned if parameter is not present
     * @return Parameter value or the default value if not present
     */
    String optFromQuery(@Nonnull String parameterName, @Nonnull String defaultValue);

    /**
     * Optionally gets from POST parameters if parameter is present
     *
     * @param parameterName Parameter name
     * @param defaultValue  Default value to be returned if parameter is not present
     * @return Parameter value or the default value if not present
     */
    String optFromPost(@Nonnull String parameterName, @Nonnull String defaultValue);

    /**
     * Returns the value of the specified request header as a String. If the request did not include a header of the
     * specified name, this method returns null. If there are multiple headers with the same name, this method returns
     * the first head in the request. The header name is case insensitive. You can use this method with any request
     * header.
     *
     * @param headerName Header name
     * @return a String containing the value of the requested header, or null if the request does not have a header
     * of that name
     */
    String getHeader(@Nonnull String headerName);

    /**
     * Returns all values for a given header (if it is present multiple times)
     *
     * @param headerName Header name
     * @return List of header values
     */
    List<String> getHeaderValues(@SuppressWarnings("SameParameterValue") @Nonnull String headerName);

    /**
     * Returns true if the request is multipart, false otherwise
     *
     * @return true if the request is multipart, false otherwise
     */
    boolean isMultipart();

    /**
     * Returns the HTTP method of the request, e.g. GET, POST, etc. Alias of {@link #getHttpMethod()}
     *
     * @return HTTP method
     */
    HttpMethod getMethod();

    /**
     * Returns the HTTP method of the request, e.g. GET, POST, etc
     *
     * @return HTTP method
     */
    HttpMethod getHttpMethod();

    /**
     * Checks if the request HTTP method matched the specified as parameter
     *
     * @param method Specified method
     * @return true if the HTTP method matches the specified, false otherwise
     */
    boolean isMethod(@Nonnull HttpMethod method);

    /**
     * Returns server data
     *
     * @return server data
     */
    ServerData getServerData();

    /**
     * Return body of the request
     *
     * @return body of the request
     */
    String getBody();

    /**
     * Returns raw query string, e.g. param1=3&param2=ff
     *
     * @return Raw query string
     */
    String getRawQueryString();

    /**
     * Returns all cookies with their string values
     *
     * @return All cookies
     */
    List<Cookie> getCookies();

    /**
     * Returns all headers with their string values
     *
     * @return
     */
    Map<String, String> getHeaders();

    final class ServerData {
        public final String serverAddress;
        public final String serverName;
        public final String serverProtocol;
        public final int serverPort;
        public final String requestMethod;
        public final String queryString;
        public final String httpAccept;
        public final String httpAcceptCharset;
        public final String httpAcceptEncoding;
        public final String httpAcceptLanguage;
        public final String httpConnection;
        public final String httpHost;
        public final String httpReferrer;
        public final String httpUserAgent;
        public final String remoteAddress;
        public final String remoteHost;
        public final int remotePort;
        public final String requestUri;
        public final String pathInfo;


        ServerData(String serverAddress,
                   String serverName,
                   String serverProtocol,
                   int serverPort,
                   String requestMethod,
                   String queryString,
                   String httpAccept,
                   String httpAcceptCharset,
                   String httpAcceptEncoding,
                   String httpAcceptLanguage,
                   String httpConnection,
                   String httpHost,
                   String httpReferrer,
                   String httpUserAgent,
                   String remoteAddress,
                   String remoteHost,
                   int remotePort,
                   String requestUri,
                   String pathInfo) {

            this.serverAddress = serverAddress;
            this.serverName = serverName;
            this.serverProtocol = serverProtocol;
            this.serverPort = serverPort;
            this.requestMethod = requestMethod;
            this.queryString = queryString;
            this.httpAccept = httpAccept;
            this.httpAcceptCharset = httpAcceptCharset;
            this.httpAcceptEncoding = httpAcceptEncoding;
            this.httpAcceptLanguage = httpAcceptLanguage;
            this.httpConnection = httpConnection;
            this.httpHost = httpHost;
            this.httpReferrer = httpReferrer;
            this.httpUserAgent = httpUserAgent;
            this.remoteAddress = remoteAddress;
            this.remoteHost = remoteHost;
            this.remotePort = remotePort;
            this.requestUri = requestUri;
            this.pathInfo = pathInfo;
        }
    }
}
