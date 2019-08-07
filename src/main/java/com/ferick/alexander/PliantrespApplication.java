package com.ferick.alexander;

import static spark.Spark.get;

import com.ferick.alexander.storage.ContractStorage;
import spark.Spark;

public class PliantrespApplication {

    public static void main(String[] args) {

        get("/storage/contracts", (request, response) -> request.pathInfo());
    }
}
