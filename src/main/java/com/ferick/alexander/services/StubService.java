package com.ferick.alexander.services;

import com.ferick.alexander.model.Contract;
import com.ferick.alexander.model.ResponseDTO;
import com.ferick.alexander.storage.ContractStorage;
import com.ferick.alexander.utils.RequestVerifier;
import com.ferick.alexander.utils.Tools;
import java.util.Optional;
import org.eclipse.jetty.http.HttpStatus;
import spark.Request;
import spark.Response;

public class StubService {

    public String responseToGet(Request request, Response response) {
        return null;
    }

    public String responseToPost(Request request, Response response) {
        String responseBody = "";

        String requestPath = request.pathInfo();
        Optional<Contract> contractOptional = ContractStorage.get(requestPath);

        if (contractOptional.isPresent()) {
            Contract contract = contractOptional.get();
            boolean isMatched = RequestVerifier.matchRequest(request, contract);

            if (contract.getResponseTimeout() != null) {
                Tools.delay(contract.getResponseTimeout());
            }

            if (isMatched) {
                ResponseDTO positiveResponse = contract.getPositiveResponse();
                if (positiveResponse != null) {
                    responseBody = formResponse(response, positiveResponse);
                } else {
                    response.status(HttpStatus.OK_200);
                }
            } else {
                ResponseDTO negativeResponse = contract.getNegativeResponse();
                if (negativeResponse != null) {
                    responseBody = formResponse(response, negativeResponse);
                } else {
                    response.status(HttpStatus.BAD_REQUEST_400);
                }
            }
        } else {
            response.status(HttpStatus.NOT_FOUND_404);
        }

        return responseBody;
    }

    public String responseToPut(Request request, Response response) {
        return null;
    }

    public String responseToPatch(Request request, Response response) {
        return null;
    }

    public String responseToDelete(Request request, Response response) {
        return null;
    }

    private String formResponse(Response response, ResponseDTO responseDTO) {
        String body = "";
        response.status(responseDTO.getStatus());
        if (responseDTO.getBody() != null) {
            body = responseDTO.getBody();
        }
        if (responseDTO.getHeaders() != null && !responseDTO.getHeaders().isEmpty()) {
            responseDTO.getHeaders().forEach(response::header);
        }
        return body;
    }
}
