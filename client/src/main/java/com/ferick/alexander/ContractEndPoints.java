package com.ferick.alexander;

import com.ferick.alexander.model.Contract;
import com.ferick.alexander.model.RequestDTO;
import feign.RequestLine;
import java.util.List;

public interface ContractEndPoints {

    @RequestLine("GET /storage/contracts")
    List<Contract> getContracts();

    @RequestLine("POST /storage/contract")
    String addContract(Contract contract);

    @RequestLine("DELETE /storage/contracts")
    String deleteContracts();

    @RequestLine("DELETE /storage/contract")
    String deleteContract(RequestDTO request);
}
