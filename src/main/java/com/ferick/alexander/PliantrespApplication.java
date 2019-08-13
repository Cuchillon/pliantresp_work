package com.ferick.alexander;

import static spark.Spark.delete;
import static spark.Spark.get;
import static spark.Spark.patch;
import static spark.Spark.post;
import static spark.Spark.put;

import com.ferick.alexander.services.ContractService;
import com.ferick.alexander.services.StubService;

public class PliantrespApplication {

    public static void main(String[] args) {

        // Methods to work with contracts
        get("/storage/contracts", new ContractService()::getContracts);
        post("/storage/contract", new ContractService()::addContract);
        delete("/storage/contracts", new ContractService()::deleteContracts);
        delete("/storage/contract", new ContractService()::deleteContract);

        // Methods to work with stubs
        get("/*", new StubService()::getResponse);
        post("/*", new StubService()::getResponse);
        put("/*", new StubService()::getResponse);
        patch("/*", new StubService()::getResponse);
        delete("/*", new StubService()::getResponse);
    }
}
