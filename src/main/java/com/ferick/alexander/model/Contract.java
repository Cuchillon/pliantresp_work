package com.ferick.alexander.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Contract {

    @JsonProperty(required = true, value = "request_path")
    private RequestPath requestPath;

    @JsonProperty(required = true)
    private RequestDTO request;

    @JsonProperty(value = "positive_response")
    private ResponseDTO positiveResponse;

    @JsonProperty(value = "negative_response")
    private ResponseDTO negativeResponse;

    @JsonProperty(value = "response_timeout")
    private Integer responseTimeout;

    public Contract() {
    }

    public RequestPath getRequestPath() {
        return requestPath;
    }

    public void setRequestPath(RequestPath requestPath) {
        this.requestPath = requestPath;
    }

    public RequestDTO getRequest() {
        return request;
    }

    public void setRequest(RequestDTO request) {
        this.request = request;
    }

    public ResponseDTO getPositiveResponse() {
        return positiveResponse;
    }

    public void setPositiveResponse(ResponseDTO positiveResponse) {
        this.positiveResponse = positiveResponse;
    }

    public ResponseDTO getNegativeResponse() {
        return negativeResponse;
    }

    public void setNegativeResponse(ResponseDTO negativeResponse) {
        this.negativeResponse = negativeResponse;
    }

    public Integer getResponseTimeout() {
        return responseTimeout;
    }

    public void setResponseTimeout(Integer responseTimeout) {
        this.responseTimeout = responseTimeout;
    }
}
