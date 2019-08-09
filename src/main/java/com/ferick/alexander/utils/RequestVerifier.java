package com.ferick.alexander.utils;

import com.ferick.alexander.model.Contract;
import com.ferick.alexander.model.RequestDTO;
import java.util.Map;
import spark.Request;

public class RequestVerifier {

    public static boolean matchRequest(Request request, Contract contract) {
        boolean isMatched = true;
        RequestDTO contractRequest = contract.getRequest();
        if (contractRequest.getMethod().equals(request.requestMethod())) {
            String requestBody = request.body().trim().replace("\n", "");

            if (!requestBody.equals(contractRequest.getBody())) {
                isMatched = false;
            }
            for (Map.Entry<String, String> entry : contractRequest.getHeaders().entrySet()) {
                String headerValue = request.headers(entry.getKey());
                if (headerValue == null || !headerValue.equals(entry.getValue())) {
                    isMatched = false;
                }
            }
        }
        return isMatched;
    }
}