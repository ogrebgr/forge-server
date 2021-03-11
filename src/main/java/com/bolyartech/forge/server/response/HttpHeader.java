package com.bolyartech.forge.server.response;

final public class HttpHeader {
    public final String header;
    public final String value;


    public HttpHeader(String header, String value) {
        this.header = header;
        this.value = value;
    }
}
