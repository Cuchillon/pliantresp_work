package com.ferick.alexander.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ferick.alexander.model.Contract;
import com.ferick.alexander.storage.ContractStorage;
import com.ferick.alexander.utils.JsonTransformer;
import java.io.IOException;
import java.util.List;
import org.eclipse.jetty.http.HttpStatus;
import spark.Request;
import spark.Response;

public class ContractService {

    public String addContract(Request request, Response response) {
        Contract contract = null;

        try {
            contract = new ObjectMapper().readValue(request.body(), Contract.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        boolean isAdded = ContractStorage.add(contract);

        if ((isAdded)) {
            response.status(HttpStatus.OK_200);
        } else {
            response.status(HttpStatus.BAD_REQUEST_400);
        }

        return "";
    }

    public String getContracts(Request request, Response response) {
        StringBuilder jsonString = new StringBuilder();
        List<Contract> contracts = ContractStorage.getAll();

        if (!contracts.isEmpty()) {
            response.status(HttpStatus.OK_200);
            response.type("application/json");
            for (Contract contract : contracts) {
                jsonString.append(JsonTransformer.toJson(contract));
            }
        } else {
            response.status(HttpStatus.NOT_FOUND_404);
            jsonString.append("Contracts list is empty");
        }

        return jsonString.toString();
    }
}
