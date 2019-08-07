package com.ferick.alexander.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;
import spark.route.HttpMethod;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RequestDTO {

    @JsonProperty(required = true)
    private HttpMethod method;
    private String body;
    private Map<String, String> headers;

    public RequestDTO() {
    }

    public HttpMethod getMethod() {
        return method;
    }

    @JsonProperty
    public void setMethod(HttpMethod method) {
        this.method = method;
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