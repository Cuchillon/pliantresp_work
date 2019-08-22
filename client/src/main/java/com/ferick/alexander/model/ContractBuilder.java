package com.ferick.alexander.model;

import com.ferick.alexander.utils.MapUtils;

public class ContractBuilder {

    private static final Integer DEFAULT_POSITIVE_STATUS = 200;
    private static final Integer DEFAULT_NEGATIVE_STATUS = 400;

    private final RequestDTO request = new RequestDTO();

    private ResponseDTO positiveResponse;
    private ResponseDTO negativeResponse;

    private Integer responseTimeout;


    public ContractBuilder(String requestPath, String requestMethod) {
        this.request.setPath(requestPath);
        this.request.setMethod(requestMethod);
    }

    public ContractBuilder withRequestBody(String requestBody) {
        this.request.setBody(requestBody);
        return this;
    }

    public ContractBuilder withRequestHeaders(String... requestHeaders) {
        this.request.setHeaders(MapUtils.asMap(requestHeaders));
        return this;
    }

    public ContractBuilder withPositiveResponseStatus(Integer positiveResponseStatus) {
        if (positiveResponse == null) {
            positiveResponse = new ResponseDTO();
        }
        positiveResponse.setStatus(positiveResponseStatus);
        return this;
    }

    public ContractBuilder withPositiveResponseBody(String positiveResponseBody) {
        if (positiveResponse == null) {
            positiveResponse = new ResponseDTO();
            positiveResponse.setStatus(DEFAULT_POSITIVE_STATUS);
        }
        positiveResponse.setBody(positiveResponseBody);
        return this;
    }

    public ContractBuilder withPositiveResponseHeaders(String... positiveResponseHeaders) {
        if (positiveResponse == null) {
            positiveResponse = new ResponseDTO();
            positiveResponse.setStatus(DEFAULT_POSITIVE_STATUS);
        }
        positiveResponse.setHeaders(MapUtils.asMap(positiveResponseHeaders));
        return this;
    }

    public ContractBuilder withNegativeResponseStatus(Integer negativeResponseStatus) {
        if (negativeResponse == null) {
            negativeResponse = new ResponseDTO();
        }
        negativeResponse.setStatus(negativeResponseStatus);
        return this;
    }

    public ContractBuilder withNegativeResponseBody(String negativeResponseBody) {
        if (negativeResponse == null) {
            negativeResponse = new ResponseDTO();
            negativeResponse.setStatus(DEFAULT_NEGATIVE_STATUS);
        }
        negativeResponse.setBody(negativeResponseBody);
        return this;
    }

    public ContractBuilder withNegativeResponseHeaders(String... negativeResponseHeaders) {
        if (negativeResponse == null) {
            negativeResponse = new ResponseDTO();
            negativeResponse.setStatus(DEFAULT_NEGATIVE_STATUS);
        }
        negativeResponse.setHeaders(MapUtils.asMap(negativeResponseHeaders));
        return this;
    }

    public ContractBuilder setResponseTimeout(Integer responseTimeout) {
        this.responseTimeout = responseTimeout;
        return this;
    }

    public Contract build() {
        Contract contract = new Contract();

        contract.setRequest(request);

        if (positiveResponse != null) {
            contract.setPositiveResponse(positiveResponse);
        }

        if (negativeResponse != null) {
            contract.setNegativeResponse(negativeResponse);
        }

        if (responseTimeout != null) {
            contract.setResponseTimeout(responseTimeout);
        }

        return contract;
    }
}
