package com.ferick.alexander.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ferick.alexander.model.Contract;
import com.ferick.alexander.model.RequestDTO;
import com.ferick.alexander.storage.ContractStorage;
import com.ferick.alexander.utils.JsonTransformer;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.eclipse.jetty.http.HttpStatus;
import spark.Request;
import spark.Response;

public class ContractService {

    /**
     * Method stores contract 'request-response' on {@link ContractStorage}
     *
     * @param request
     * @param response
     * @return response body containing message whether contract is stored or not
     */
    public String addContract(Request request, Response response) {
        String responseBody;
        Contract contract = null;
        boolean isAdded = false;

        try {
            contract = new ObjectMapper().readValue(request.body(), Contract.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (contract != null
                && contract.getRequest().getPath() != null
                && contract.getRequest().getMethod() != null) {
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

    /**
     * Method gets all contracts 'request-response' stored on {@link ContractStorage}
     *
     * @param request
     * @param response
     * @return list of stored contracts, if there are no contracts returns empty list
     */
    public String getContracts(Request request, Response response) {
        List<String> jsonList = new ArrayList<>();
        List<Contract> contracts = ContractStorage.getAll();

        response.status(HttpStatus.OK_200);
        response.type("application/json");
        if (!contracts.isEmpty()) {
            for (Contract contract : contracts) {
                jsonList.add(JsonTransformer.toJson(contract));
            }
        }

        return jsonList.toString();
    }

    /**
     * Method deletes contract 'request-response' from {@link ContractStorage}
     * matching path and request method
     *
     * @param request
     * @param response
     * @return response body containing message whether contract is deleted
     * or there is no suitable contract in the storage
     */
    public String deleteContract(Request request, Response response) {
        String responseBody = "";
        RequestDTO requestDTO = null;

        try {
            requestDTO = new ObjectMapper().readValue(request.body(), RequestDTO.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (requestDTO != null
                && requestDTO.getPath() != null
                && requestDTO.getMethod() != null) {
            boolean isDeleted = ContractStorage.delete(requestDTO);
            response.status(HttpStatus.OK_200);
            if ((isDeleted)) {
                responseBody = "Contract is deleted";
            } else {
                responseBody = "Contract list does not contain any suitable contract";
            }
        } else {
            response.status(HttpStatus.BAD_REQUEST_400);
        }

        return responseBody;
    }

    /**
     * Method clears contract list
     *
     * @param request
     * @param response
     * @return response body containing message whether contract list is cleared or not
     */
    public String deleteContracts(Request request, Response response) {
        String responseBody;

        ContractStorage.clear();

        if ((ContractStorage.count() == 0)) {
            response.status(HttpStatus.OK_200);
            responseBody = "Contract list is empty";
        } else {
            response.status(HttpStatus.INTERNAL_SERVER_ERROR_500);
            responseBody = "Contract list is not empty";
        }

        return responseBody;
    }
}
