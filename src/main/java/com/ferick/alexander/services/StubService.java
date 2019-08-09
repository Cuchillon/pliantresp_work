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
        StringBuilder jsonString = new StringBuilder();

        String requestPath = request.pathInfo();
        Optional<Contract> contractOptional = ContractStorage.get(requestPath);

        contractOptional.ifPresent(contract -> {
            boolean isMatched = RequestVerifier.matchRequest(request, contract);

            if (contract.getResponseTimeout() != null) {
                Tools.delay(contract.getResponseTimeout());
            }

            if (isMatched) {
                ResponseDTO positiveResponse = contract.getPositiveResponse();
                if (positiveResponse != null) {
                    formResponse(response, jsonString, positiveResponse);
                } else {
                    response.status(HttpStatus.OK_200);
                }
            } else {
                ResponseDTO negativeResponse = contract.getNegativeResponse();
                if (negativeResponse != null) {
                    formResponse(response, jsonString, negativeResponse);
                } else {
                    response.status(HttpStatus.NOT_FOUND_404);
                }
            }
        });

        return jsonString.toString();
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

    private void formResponse(Response response, StringBuilder jsonString, ResponseDTO responseDTO) {
        response.status(responseDTO.getStatus());
        if (responseDTO.getBody() != null) {
            jsonString.append(responseDTO.getBody());
        }
        if (responseDTO.getHeaders() != null && !responseDTO.getHeaders().isEmpty()) {
            responseDTO.getHeaders().forEach(response::header);
        }
    }
}
