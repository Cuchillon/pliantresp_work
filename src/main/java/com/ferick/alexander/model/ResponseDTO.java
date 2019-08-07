package com.ferick.alexander.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;
import org.eclipse.jetty.http.HttpStatus;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseDTO {

    @JsonProperty(required = true)
    private HttpStatus status;
    private String body;
    private Map<String, String> headers;

    public ResponseDTO() {
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
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