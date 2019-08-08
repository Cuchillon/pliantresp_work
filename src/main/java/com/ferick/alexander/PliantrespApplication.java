package com.ferick.alexander;

import static spark.Spark.delete;
import static spark.Spark.get;
import static spark.Spark.post;

import com.ferick.alexander.services.ContractService;

public class PliantrespApplication {

    public static void main(String[] args) {

        get("/storage/contracts", new ContractService()::getContracts);

        post("/storage/contract", new ContractService()::addContract);

        delete("/storage/contracts", new ContractService()::deleteContracts);

        delete("/storage/contract", new ContractService()::deleteContract);
    }
}
