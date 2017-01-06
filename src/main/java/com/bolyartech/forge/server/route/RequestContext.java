package com.bolyartech.forge.server.route;

import com.bolyartech.forge.server.HttpMethod;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.Part;
import java.io.IOException;
import java.util.List;


public interface RequestContext {
    String getFromGet(String parameterName);

    String getFromPost(String parameterName);

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

    String getRoutePath();

    String getScheme();

    Part getPart(String partName) throws IOException, ServletException;

    String getPathInfoString();

    Cookie getCookie(String cookieName);

    String optFromGet(String parameterName, String defaultValue);

    String optFromPost(String parameterName, String defaultValue);

    String optFromPathInfo(String parameterName, String defaultValue);

    String optCookie(String cookieName, String defaultValue);

    String getHeader(String headerName);

    List<String> getHeaderValues(String headerName);

    boolean isMultipart();

    HttpMethod getMethod();

    HttpMethod getHttpMethod();

    boolean isMethod(HttpMethod method);

    ServerData getServerData();


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
        public final String httpReferer;
        public final String httpUserAgent;
        public final String remoteAddress;
        public final String remoteHost;
        public final int remotePort;
        public final String requestUri;
        public final String pathInfo;


        public ServerData(String serverAddress,
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
                          String httpReferer,
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
            this.httpReferer = httpReferer;
            this.httpUserAgent = httpUserAgent;
            this.remoteAddress = remoteAddress;
            this.remoteHost = remoteHost;
            this.remotePort = remotePort;
            this.requestUri = requestUri;
            this.pathInfo = pathInfo;
        }
    }
}
