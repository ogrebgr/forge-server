package com.bolyartech.forge.server.route;

import com.bolyartech.forge.server.HttpMethod;
import com.bolyartech.forge.server.response.HttpHeaders;
import com.bolyartech.forge.server.session.Session;
import com.bolyartech.forge.server.session.SessionImpl;
import com.google.common.base.Strings;
import com.google.common.io.CharStreams;

import javax.annotation.Nonnull;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.*;


/**
 * Request context
 *
 * @see RequestContext
 */
public class RequestContextImpl implements RequestContext {
    private static final String HEADER_CONTENT_TYPE = "Content-Type";
    private static final String CONTENT_TYPE_FORM_ENCODED = "application/x-www-form-urlencoded";
    private static final String CONTENT_TYPE_MULTIPART = "multipart/form-data";


    private final HttpServletRequest httpReq;
    private final Map<String, String> queryParams = new HashMap<>();
    private final Map<String, String> postParams = new HashMap<>();
    private final Map<String, Cookie> cookieParams = new HashMap<>();
    private final List<String> pathInfoParams = new ArrayList<>();
    private final String routePath;
    private final String pathInfoString;
    private Session session;
    private boolean cookiesInitialized = false;
    private boolean isMultipart;
    private ServerData serverData;
    private String body;

    private boolean isBodyConsumed = false;
    private boolean areGetParametersExtracted = false;
    private boolean arePostParametersExtracted = false;
    private boolean arePiParametersExtracted = false;


    /**
     * Creates new RequestContextImpl
     *
     * @param httpReq   HTTP servlet request
     * @param routePath route path
     * @throws IOException if there is a problem creating the context
     */
    public RequestContextImpl(@Nonnull HttpServletRequest httpReq, @Nonnull String routePath) {
        this.httpReq = httpReq;

        this.routePath = routePath;

        pathInfoString = this.httpReq.getPathInfo().substring(routePath.length());
    }


    static void extractParameters(@Nonnull String queryString, @Nonnull Map<String, String> to) {
        if (!Strings.isNullOrEmpty(queryString)) {
            try {
                String[] split = queryString.split("&");
                for (String aSplit : split) {
                    String[] keyValue = aSplit.split("=");
                    if (keyValue.length == 1) {
                        to.put(URLDecoder.decode(keyValue[0], "UTF-8"), "");
                    } else {
                        to.put(URLDecoder.decode(keyValue[0], "UTF-8"), URLDecoder.decode(keyValue[1], "UTF-8"));
                    }
                }
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
        }
    }


    @Override
    public Session getSession() {
        if (session == null) {
            session = new SessionImpl(httpReq.getSession());
        }

        return session;
    }


    @Override
    public String getFromGet(@Nonnull String parameterName) {
        if (!areGetParametersExtracted) {
            extractParameters(httpReq.getQueryString(), queryParams);
            areGetParametersExtracted = true;
        }
        return queryParams.get(parameterName);
    }


    @Override
    public String getFromQuery(@Nonnull String parameterName) {
        return getFromGet(parameterName);
    }


    @Override
    public String getFromPost(@Nonnull String parameterName) {
        if (!arePostParametersExtracted) {
            try {
                extractPostParameters();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        return postParams.get(parameterName);
    }


    private void extractPostParameters() throws IOException {
        String body = getBody();
        if (httpReq.getMethod().equalsIgnoreCase(HttpMethod.POST.getLiteral())) {
            String contentType = httpReq.getHeader(HEADER_CONTENT_TYPE);
            if (contentType != null) {
                if (contentType.toLowerCase().contains(CONTENT_TYPE_FORM_ENCODED.toLowerCase())) {
                    extractParameters(body, postParams);
                } else if (contentType.toLowerCase().contains(CONTENT_TYPE_MULTIPART.toLowerCase())) {
                    isMultipart = true;
                }
            }
        }

        arePostParametersExtracted = true;
    }


    @Override
    public List<String> getPathInfoParameters() {
        if (!arePiParametersExtracted) {
            extractPiParameters();
        }
        return pathInfoParams;
    }


    private void extractPiParameters() {
        //protection against directory traversal. Jetty never sends '..' here but other containers may do so...
        if (pathInfoParams.contains("..")) {
            throw new IllegalStateException("Path info contains '..'");
        }

        String[] piRaw = pathInfoString.split("/");
        for (String s : piRaw) {
            if (s.trim().length() > 0) {
                pathInfoParams.add(s);
            }
        }

        Collections.reverse(pathInfoParams);
        arePiParametersExtracted = true;
    }


    @Override
    public List<String> getPi() {
        return getPathInfoParameters();
    }


    @Override
    public String getRoutePath() {
        return routePath;
    }


    @Override
    public String getScheme() {
        return httpReq.getScheme().toLowerCase();
    }


    @Override
    public Part getPart(@Nonnull String partName) throws IOException, ServletException {
        if (arePostParametersExtracted) {
            throw new IllegalStateException("You can either user getFromPost() or getPart() in handling a request but not both");
        }
        return httpReq.getPart(partName);
    }


    @Override
    public String getPathInfoString() {
        return pathInfoString;
    }


    @Override
    public Cookie getCookie(@Nonnull String cookieName) {
        initializeCookies();

        return cookieParams.get(cookieName);
    }

    private void initializeCookies() {
        if (!cookiesInitialized) {
            Cookie[] cs = httpReq.getCookies();
            if (cs != null) {
                for (Cookie c : cs) {
                    cookieParams.put(c.getName(), c);
                }
            }
            cookiesInitialized = true;
        }
    }

    @Override
    public String optFromGet(@Nonnull String parameterName, @Nonnull String defaultValue) {
        String ret = getFromGet(parameterName);
        if (ret == null) {
            ret = defaultValue;
        }

        return ret;
    }


    @Override
    public String optFromQuery(@Nonnull String parameterName, @Nonnull String defaultValue) {
        return optFromGet(parameterName, defaultValue);
    }


    @Override
    public String optFromPost(@Nonnull String parameterName, @Nonnull String defaultValue) {
        String ret = getFromPost(parameterName);
        if (ret == null) {
            ret = defaultValue;
        }

        return ret;
    }


    @Override
    public String getHeader(@Nonnull String headerName) {
        return httpReq.getHeader(headerName);
    }


    @Override
    public List<String> getHeaderValues(@Nonnull String headerName) {
        Enumeration<String> values = httpReq.getHeaders(headerName);
        if (values != null) {
            return Collections.list(values);
        } else {
            return null;
        }
    }


    @Override
    public boolean isMultipart() {
        return isMultipart;
    }


    @Override
    public HttpMethod getMethod() {
        return getHttpMethod();
    }


    @Override
    public HttpMethod getHttpMethod() {
        switch (httpReq.getMethod().toLowerCase()) {
            case "get":
                return HttpMethod.GET;
            case "post":
                return HttpMethod.POST;
            case "put":
                return HttpMethod.PUT;
            case "delete":
                return HttpMethod.DELETE;
            default:
                return null;
        }
    }


    @Override
    public boolean isMethod(@Nonnull HttpMethod method) {
        return httpReq.getMethod().toLowerCase().equals(method.getLiteral().toLowerCase());
    }


    @Override
    public ServerData getServerData() {
        if (serverData == null) {
            serverData = new ServerData(httpReq.getLocalAddr(),
                    httpReq.getServerName(),
                    httpReq.getProtocol(),
                    httpReq.getLocalPort(),
                    httpReq.getMethod(),
                    httpReq.getQueryString(),
                    httpReq.getHeader(HttpHeaders.ACCEPT),
                    httpReq.getHeader(HttpHeaders.ACCEPT_CHARSET),
                    httpReq.getHeader(HttpHeaders.ACCEPT_ENCODING),
                    httpReq.getHeader(HttpHeaders.ACCEPT_LANGUAGE),
                    httpReq.getHeader(HttpHeaders.CONNECTION),
                    httpReq.getHeader(HttpHeaders.HOST),
                    httpReq.getHeader(HttpHeaders.REFERRER),
                    httpReq.getHeader(HttpHeaders.USER_AGENT),
                    httpReq.getRemoteAddr(),
                    httpReq.getRemoteHost(),
                    httpReq.getRemotePort(),
                    httpReq.getRequestURI(),
                    httpReq.getPathInfo());
        }

        return serverData;
    }


    @Override
    public String getBody()  {
        if (!isBodyConsumed) {
            try {
                body = CharStreams.toString(httpReq.getReader());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            isBodyConsumed = true;
        }
        return body;
    }


    @Override
    public String getRawQueryString() {
        return httpReq.getQueryString();
    }


    @Override
    public Map<String, String> getQueryStringParameters() {
        if (!areGetParametersExtracted) {
            extractParameters(httpReq.getQueryString(), queryParams);
        }

        return queryParams;
    }


    @Override
    public List<Cookie> getCookies() {
        List<Cookie> ret = new ArrayList<>();

        Cookie[] cs = httpReq.getCookies();
        if (cs != null) {
            ret.addAll(Arrays.asList(cs));
        }

        return ret;
    }


    @Override
    public Map<String, String> getHeaders() {
        List<String> hs = Collections.list(httpReq.getHeaderNames());

        Map<String, String> ret = new HashMap<>();
        for (String h : hs) {
            ret.put(h, httpReq.getHeader(h));
        }

        return ret;
    }
}
