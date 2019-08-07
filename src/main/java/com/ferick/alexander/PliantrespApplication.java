package com.ferick.alexander;

import static spark.Spark.get;
import static spark.Spark.post;

import com.ferick.alexander.services.ContractService;

public class PliantrespApplication {

    public static void main(String[] args) {

        get("/storage/contracts", (request, response) -> request.pathInfo());

        post("/storage/contracts", (request, response) -> new ContractService().addContract(request.body()));
    }
}
