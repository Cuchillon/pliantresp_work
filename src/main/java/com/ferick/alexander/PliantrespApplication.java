package com.ferick.alexander;

import static spark.Spark.get;
import static spark.Spark.post;

import com.ferick.alexander.services.ContractService;

public class PliantrespApplication {

    public static void main(String[] args) {

        get("/storage/contracts", new ContractService()::getContracts);

        post("/storage/contracts", new ContractService()::addContract);
    }
}
