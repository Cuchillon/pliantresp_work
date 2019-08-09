package com.ferick.alexander.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ferick.alexander.model.Contract;
import com.ferick.alexander.model.RequestPath;
import com.ferick.alexander.storage.ContractStorage;
import com.ferick.alexander.utils.JsonTransformer;
import java.io.IOException;
import java.util.List;
import org.eclipse.jetty.http.HttpStatus;
import spark.Request;
import spark.Response;

public class ContractService {

    public String addContract(Request request, Response response) {
        StringBuilder jsonString = new StringBuilder();
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
            jsonString.append("Contract is added");
        } else {
            response.status(HttpStatus.BAD_REQUEST_400);
            jsonString.append("Contract is not added");
        }

        return jsonString.toString();
    }

    public String getContracts(Request request, Response response) {
        StringBuilder jsonString = new StringBuilder();
        jsonString.append('[');
        List<Contract> contracts = ContractStorage.getAll();

        if (!contracts.isEmpty()) {
            response.status(HttpStatus.OK_200);
            response.type("application/json");
            for (Contract contract : contracts) {
                jsonString.append(JsonTransformer.toJson(contract));
                jsonString.append(',');
            }
            jsonString.deleteCharAt(jsonString.length() - 1);
            jsonString.append(']');
        } else {
            response.status(HttpStatus.NOT_FOUND_404);
            jsonString.append("Contracts list is empty");
        }

        return jsonString.toString();
    }

    public String deleteContract(Request request, Response response) {
        StringBuilder jsonString = new StringBuilder();
        RequestPath requestPath = null;

        try {
            requestPath = new ObjectMapper().readValue(request.body(), RequestPath.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        boolean isDeleted = ContractStorage.delete(requestPath);

        if ((isDeleted)) {
            response.status(HttpStatus.OK_200);
            jsonString.append("Contract is deleted");
        } else {
            response.status(HttpStatus.BAD_REQUEST_400);
            jsonString.append("Contract is not deleted");
        }

        return jsonString.toString();
    }

    public String deleteContracts(Request request, Response response) {
        StringBuilder jsonString = new StringBuilder();

        ContractStorage.clear();

        if ((ContractStorage.count() == 0)) {
            response.status(HttpStatus.OK_200);
            jsonString.append("Contracts list is empty");
        } else {
            response.status(HttpStatus.INTERNAL_SERVER_ERROR_500);
            jsonString.append("Contracts list is not empty");
        }

        return jsonString.toString();
    }
}
