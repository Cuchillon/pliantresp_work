package com.ferick.alexander.services;

import com.ferick.alexander.model.Contract;
import com.ferick.alexander.model.RequestDTO;
import com.ferick.alexander.model.ResponseDTO;
import com.ferick.alexander.storage.ContractStorage;
import com.ferick.alexander.storage.RequestStorage;
import com.ferick.alexander.utils.RequestVerifier;
import com.ferick.alexander.utils.Tools;

import java.util.Map;
import java.util.Optional;
import java.util.Set;
import org.eclipse.jetty.http.HttpStatus;
import spark.Request;
import spark.Response;

public class StubService {

    public String getResponse(Request request, Response response) {
        String responseBody = "";

        storeRequest(request);

        String requestPath = request.pathInfo();
        Optional<Contract> contractOptional = ContractStorage.get(requestPath, request.requestMethod());

        if (contractOptional.isPresent()) {
            Contract contract = contractOptional.get();
            boolean isMatched = RequestVerifier.matchRequest(request, contract);
            Map<String, String> pathParams =
                    RequestVerifier.getPathParams(requestPath, contract.getRequest().getPath());

            if (contract.getResponseTimeout() != null) {
                Tools.delay(contract.getResponseTimeout());
            }

            if (isMatched) {
                ResponseDTO positiveResponse = contract.getPositiveResponse();
                if (positiveResponse != null) {
                    responseBody = formResponse(response, positiveResponse, pathParams);
                } else {
                    response.status(HttpStatus.OK_200);
                }
            } else {
                ResponseDTO negativeResponse = contract.getNegativeResponse();
                if (negativeResponse != null) {
                    responseBody = formResponse(response, negativeResponse, pathParams);
                } else {
                    response.status(HttpStatus.BAD_REQUEST_400);
                }
            }
        } else {
            response.status(HttpStatus.NOT_FOUND_404);
        }

        return responseBody;
    }

    private String formResponse(Response response, ResponseDTO responseDTO, Map<String, String> pathParams) {
        String body = "";
        response.status(responseDTO.getStatus());
        String bodyTemplate = responseDTO.getBody();
        if (bodyTemplate != null) {
            if (pathParams.isEmpty()) {
                body = bodyTemplate;
            } else {
                body = RequestVerifier.checkPathParams(pathParams, bodyTemplate);
            }
        }
        if (responseDTO.getHeaders() != null && !responseDTO.getHeaders().isEmpty()) {
            responseDTO.getHeaders().forEach(response::header);
        }
        return body;
    }

    private void storeRequest(Request request) {
        RequestDTO requestDTO = new RequestDTO();
        requestDTO.setPath(request.pathInfo());
        requestDTO.setMethod(request.requestMethod());
        requestDTO.setBody(request.body());
        Set<String> headers = request.headers();
        for (String header : headers) {
            requestDTO.getHeaders().put(header, request.headers(header));
        }

        RequestStorage.add(requestDTO);
    }
}
