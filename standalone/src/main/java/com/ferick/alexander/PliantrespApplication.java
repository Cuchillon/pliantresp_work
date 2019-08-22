package com.ferick.alexander;

import static spark.Spark.delete;
import static spark.Spark.get;
import static spark.Spark.patch;
import static spark.Spark.path;
import static spark.Spark.post;
import static spark.Spark.put;

import com.ferick.alexander.services.ContractService;
import com.ferick.alexander.services.RequestService;
import com.ferick.alexander.services.StubService;

public class PliantrespApplication {

    public static void main(String[] args) {
        path("/storage", PliantrespApplication::storageController);
        path("/", PliantrespApplication::stubController);
    }

    private static void storageController() {
        ContractService contractService = new ContractService();
        RequestService requestService = new RequestService();

        // Methods to work with contracts
        get("/contracts", contractService::getContracts);
        post("/contract", contractService::addContract);
        delete("/contracts", contractService::deleteContracts);
        delete("/contract", contractService::deleteContract);

        // Methods to get stored requests
        get("/requests", requestService::getRequests);
        post("/request", requestService::getRequest);
        delete("/requests", requestService::deleteRequests);
    }

    private static void stubController() {
        StubService stubService = new StubService();

        // Methods to work with stubs
        get("*", stubService::getResponse);
        post("*", stubService::getResponse);
        put("*", stubService::getResponse);
        patch("*", stubService::getResponse);
        delete("*", stubService::getResponse);
    }
}
