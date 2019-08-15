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

    public Contract setRequestPath(RequestPath requestPath) {
        this.requestPath = requestPath;
        return this;
    }

    public RequestDTO getRequest() {
        return request;
    }

    public Contract setRequest(RequestDTO request) {
        this.request = request;
        return this;
    }

    public ResponseDTO getPositiveResponse() {
        return positiveResponse;
    }

    public Contract setPositiveResponse(ResponseDTO positiveResponse) {
        this.positiveResponse = positiveResponse;
        return this;
    }

    public ResponseDTO getNegativeResponse() {
        return negativeResponse;
    }

    public Contract setNegativeResponse(ResponseDTO negativeResponse) {
        this.negativeResponse = negativeResponse;
        return this;
    }

    public Integer getResponseTimeout() {
        return responseTimeout;
    }

    public Contract setResponseTimeout(Integer responseTimeout) {
        this.responseTimeout = responseTimeout;
        return this;
    }
}
