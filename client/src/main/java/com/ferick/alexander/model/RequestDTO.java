package com.ferick.alexander.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.HashMap;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RequestDTO {

    @JsonProperty(required = true)
    private String path;

    @JsonProperty(required = true)
    private String method;

    private String body = "";
    private Map<String, String> headers = new HashMap<>();

    public RequestDTO() {
    }

    public String getPath() {
        return path;
    }

    public RequestDTO setPath(String path) {
        this.path = path;
        return this;
    }

    public String getMethod() {
        return method;
    }

    public RequestDTO setMethod(String method) {
        this.method = method;
        return this;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }
}
