package com.bolyartech.forge.server.route;

import com.bolyartech.forge.server.HttpMethod;
import com.bolyartech.forge.server.response.HttpHeaders;
import com.google.common.base.Strings;
import com.google.common.io.CharStreams;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.*;


public class RequestContextImpl implements RequestContext {
    private static final String HEADER_CONTENT_TYPE = "Content-Type";
    private static final String CONTENT_TYPE_FORM_ENCODED = "application/x-www-form-urlencoded";
    private static final String CONTENT_TYPE_MULTIPART = "multipart/form-data";


    private final HttpServletRequest mHttpReq;
    private final Map<String, String> mGetParams = new HashMap<>();
    private final Map<String, String> mPostParams = new HashMap<>();
    private final Map<String, Cookie> mCookieParams = new HashMap<>();
    private final List<String> mPathInfoParams = new ArrayList<>();
    private final String mRoutePath;
    private final String mPathInfoString;
    private boolean mCookiesInitialized = false;
    private boolean mIsMultipart;
    private ServerData mServerData;


    public RequestContextImpl(HttpServletRequest httpReq, String routePath) throws IOException {
        mHttpReq = httpReq;
        extractParameters(httpReq.getQueryString(), mGetParams);

        if (httpReq.getMethod().equalsIgnoreCase(HttpMethod.POST.getLiteral())) {
            String contentType = httpReq.getHeader(HEADER_CONTENT_TYPE);
            if (contentType.toLowerCase().contains(CONTENT_TYPE_FORM_ENCODED.toLowerCase())) {
                extractParameters(CharStreams.toString(httpReq.getReader()), mPostParams);
            } else if (contentType.toLowerCase().contains(CONTENT_TYPE_MULTIPART.toLowerCase())) {
                mIsMultipart = true;
            }
        }

        mRoutePath = routePath;
        mPathInfoString = mHttpReq.getPathInfo().replace(routePath, "");

        String[] piRaw = mPathInfoString.split("/");
        for (String s : piRaw) {
            if (s.trim().length() > 0) {
                mPathInfoParams.add(s);
            }
        }

    }


    static void extractParameters(String queryString, Map<String, String> to) {
        if (!Strings.isNullOrEmpty(queryString)) {
            try {
                String decoded = URLDecoder.decode(queryString, "UTF-8");
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
    public String getFromGet(String parameterName) {
        return mGetParams.get(parameterName);
    }


    @Override
    public String getFromPost(String parameterName) {
        return mPostParams.get(parameterName);
    }


    @Override
    public List<String> getPathInfoParameters() {
        return mPathInfoParams;
    }


    @Override
    public List<String> getPi() {
        return getPathInfoParameters();
    }


    @Override
    public String getRoutePath() {
        return mRoutePath;
    }


    @Override
    public String getScheme() {
        return mHttpReq.getScheme().toLowerCase();
    }


    @Override
    public Part getPart(String partName) throws IOException, ServletException {
        return mHttpReq.getPart(partName);
    }


    @Override
    public String getPathInfoString() {
        return mPathInfoString;
    }


    @Override
    public Cookie getCookie(String cookieName) {
        if (!mCookiesInitialized) {
            Cookie[] cs = mHttpReq.getCookies();
            for (Cookie c : cs) {
                mCookieParams.put(c.getName(), c);
            }
            mCookiesInitialized = true;
        }

        return mCookieParams.get(cookieName);
    }


    @Override
    public String optFromGet(String parameterName, String defaultValue) {
        return null;
    }


    @Override
    public String optFromPost(String parameterName, String defaultValue) {
        return null;
    }


    @Override
    public String optFromPathInfo(String parameterName, String defaultValue) {
        return null;
    }


    @Override
    public String optCookie(String cookieName, String defaultValue) {
        return null;
    }


    @Override
    public String getHeader(String headerName) {
        return mHttpReq.getHeader(headerName);
    }


    @Override
    public List<String> getHeaderValues(String headerName) {
        Enumeration<String> values = mHttpReq.getHeaders(headerName);
        if (values != null) {
            return Collections.list(values);
        } else {
            return null;
        }
    }


    @Override
    public boolean isMultipart() {
        return mIsMultipart;
    }


    @Override
    public HttpMethod getMethod() {
        return getHttpMethod();
    }


    @Override
    public HttpMethod getHttpMethod() {
        switch (mHttpReq.getMethod().toLowerCase()) {
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
    public boolean isMethod(HttpMethod method) {
        return mHttpReq.getMethod().toLowerCase().equals(method.getLiteral().toLowerCase());
    }


    @Override
    public ServerData getServerData() {
        if (mServerData == null) {
            mServerData = new ServerData(mHttpReq.getLocalAddr(),
                    mHttpReq.getServerName(),
                    mHttpReq.getProtocol(),
                    mHttpReq.getLocalPort(),
                    mHttpReq.getMethod(),
                    mHttpReq.getQueryString(),
                    mHttpReq.getHeader(HttpHeaders.ACCEPT),
                    mHttpReq.getHeader(HttpHeaders.ACCEPT_CHARSET),
                    mHttpReq.getHeader(HttpHeaders.ACCEPT_ENCODING),
                    mHttpReq.getHeader(HttpHeaders.ACCEPT_LANGUAGE),
                    mHttpReq.getHeader(HttpHeaders.CONNECTION),
                    mHttpReq.getHeader(HttpHeaders.HOST),
                    mHttpReq.getHeader(HttpHeaders.REFERER),
                    mHttpReq.getHeader(HttpHeaders.USER_AGENT),
                    mHttpReq.getRemoteAddr(),
                    mHttpReq.getRemoteHost(),
                    mHttpReq.getRemotePort(),
                    mHttpReq.getRequestURI(),
                    mHttpReq.getPathInfo()
            );
        }

        return mServerData;
    }
}
