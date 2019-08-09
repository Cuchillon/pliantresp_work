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
        get("/*", new StubService()::responseToGet);
        post("/*", new StubService()::responseToPost);
        put("/*", new StubService()::responseToPut);
        patch("/*", new StubService()::responseToPatch);
        delete("/*", new StubService()::responseToDelete);
    }
}
