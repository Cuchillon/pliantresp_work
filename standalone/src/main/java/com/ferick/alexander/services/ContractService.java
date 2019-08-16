package com.ferick.alexander.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ferick.alexander.model.Contract;
import com.ferick.alexander.model.RequestPath;
import com.ferick.alexander.storage.ContractStorage;
import com.ferick.alexander.utils.JsonTransformer;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.eclipse.jetty.http.HttpStatus;
import spark.Request;
import spark.Response;

public class ContractService {

    public String addContract(Request request, Response response) {
        String responseBody;
        Contract contract = null;
        boolean isAdded = false;

        try {
            contract = new ObjectMapper().readValue(request.body(), Contract.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (contract != null) {
            isAdded = ContractStorage.add(contract);
        }

        if ((isAdded)) {
            response.status(HttpStatus.OK_200);
            responseBody = "Contract is added";
        } else {
            response.status(HttpStatus.BAD_REQUEST_400);
            responseBody = "Contract is not added";
        }

        return responseBody;
    }

    public String getContracts(Request request, Response response) {
        List<String> jsonList = new ArrayList<>();
        List<Contract> contracts = ContractStorage.getAll();

        if (!contracts.isEmpty()) {
            response.status(HttpStatus.OK_200);
            response.type("application/json");
            for (Contract contract : contracts) {
                jsonList.add(JsonTransformer.toJson(contract));
            }
        } else {
            response.status(HttpStatus.NOT_FOUND_404);
        }

        return jsonList.toString();
    }

    public String deleteContract(Request request, Response response) {
        String responseBody;
        RequestPath requestPath = null;
        boolean isDeleted = false;

        try {
            requestPath = new ObjectMapper().readValue(request.body(), RequestPath.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (requestPath != null) {
            isDeleted = ContractStorage.delete(requestPath);
        }

        if ((isDeleted)) {
            response.status(HttpStatus.OK_200);
            responseBody = "Contract is deleted";
        } else {
            response.status(HttpStatus.BAD_REQUEST_400);
            responseBody = "Contract is not deleted";
        }

        return responseBody;
    }

    public String deleteContracts(Request request, Response response) {
        String responseBody;

        ContractStorage.clear();

        if ((ContractStorage.count() == 0)) {
            response.status(HttpStatus.OK_200);
            responseBody = "Contracts list is empty";
        } else {
            response.status(HttpStatus.INTERNAL_SERVER_ERROR_500);
            responseBody = "Contracts list is not empty";
        }

        return responseBody;
    }
}
