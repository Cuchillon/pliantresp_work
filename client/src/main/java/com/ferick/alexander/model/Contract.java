package com.ferick.alexander.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonDeserialize(builder = Contract.Builder.class)
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

    public RequestDTO getRequest() {
        return request;
    }

    public ResponseDTO getPositiveResponse() {
        return positiveResponse;
    }

    public ResponseDTO getNegativeResponse() {
        return negativeResponse;
    }

    public Integer getResponseTimeout() {
        return responseTimeout;
    }

    @JsonPOJOBuilder
    public static class Builder {
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

        public Builder withRequestPath(RequestPath requestPath) {
            this.requestPath = requestPath;
            return this;
        }

        public Builder withRequest(RequestDTO request) {
            this.request = request;
            return this;
        }

        public Builder withPositiveResponse(ResponseDTO positiveResponse) {
            this.positiveResponse = positiveResponse;
            return this;
        }

        public Builder withNegativeResponse(ResponseDTO negativeResponse) {
            this.negativeResponse = negativeResponse;
            return this;
        }

        public Builder withResponseTimeout(Integer responseTimeout) {
            this.responseTimeout = responseTimeout;
            return this;
        }

        public Contract build() {
            Contract contract = new Contract();
            contract.requestPath = requestPath;
            contract.request = request;
            contract.positiveResponse = positiveResponse;
            contract.negativeResponse = negativeResponse;
            contract.responseTimeout = responseTimeout;
            return contract;
        }
    }
}
