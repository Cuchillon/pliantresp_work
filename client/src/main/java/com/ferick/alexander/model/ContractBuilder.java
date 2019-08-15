package com.ferick.alexander.model;

import java.util.Map;

public class ContractBuilder {

    private String requestPath;
    private String requestMethod;
    private String requestBody;
    private Map<String, String> requestHeaders;
    private Integer positiveResponseStatus;
    private String positiveResponseBody;
    private Map<String, String> positiveResponseHeaders;
    private Integer negativeResponseStatus;
    private String negativeResponseBody;
    private Map<String, String> negativeResponseHeaders;
    private Integer responseTimeout;


    public ContractBuilder(String requestPath, String requestMethod) {
        this.requestPath = requestPath;
        this.requestMethod = requestMethod;
    }

    public ContractBuilder withRequestBody(String requestBody) {
        this.requestBody = requestBody;
        return this;
    }

    public ContractBuilder withRequestHeaders(Map<String, String> requestHeaders) {
        this.requestHeaders = requestHeaders;
        return this;
    }

    public ContractBuilder setPositiveResponseStatus(Integer positiveResponseStatus) {
        this.positiveResponseStatus = positiveResponseStatus;
        return this;
    }

    public ContractBuilder setPositiveResponseBody(String positiveResponseBody) {
        this.positiveResponseBody = positiveResponseBody;
        return this;
    }

    public ContractBuilder setPositiveResponseHeaders(Map<String, String> positiveResponseHeaders) {
        this.positiveResponseHeaders = positiveResponseHeaders;
        return this;
    }

    public ContractBuilder setNegativeResponseStatus(Integer negativeResponseStatus) {
        this.negativeResponseStatus = negativeResponseStatus;
        return this;
    }

    public ContractBuilder setNegativeResponseBody(String negativeResponseBody) {
        this.negativeResponseBody = negativeResponseBody;
        return this;
    }

    public ContractBuilder setNegativeResponseHeaders(Map<String, String> negativeResponseHeaders) {
        this.negativeResponseHeaders = negativeResponseHeaders;
        return this;
    }

    public ContractBuilder setResponseTimeout(Integer responseTimeout) {
        this.responseTimeout = responseTimeout;
        return this;
    }

    public Contract build() {
        Contract contract = new Contract();

        RequestPath path = new RequestPath();
        path.setPath(requestPath);

        RequestDTO request = new RequestDTO();
        request.setMethod(requestMethod);
        if (requestBody != null) {
            request.setBody(requestBody);
        }
        if (requestHeaders != null) {
            request.setHeaders(requestHeaders);
        }

        return contract;
    }
}
