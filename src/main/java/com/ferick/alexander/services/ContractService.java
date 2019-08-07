package com.ferick.alexander.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ferick.alexander.model.Contract;
import com.ferick.alexander.storage.ContractStorage;
import java.io.IOException;

public class ContractService {

    public boolean addContract(String body) {
        Contract contract = null;

        try {
            contract = new ObjectMapper().readValue(body, Contract.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return ContractStorage.add(contract);
    }
}
