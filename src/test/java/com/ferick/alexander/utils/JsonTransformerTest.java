package com.ferick.alexander.utils;

import static org.junit.Assert.assertEquals;

import com.ferick.alexander.model.Contract;
import com.ferick.alexander.model.RequestDTO;
import com.ferick.alexander.model.RequestPath;
import com.ferick.alexander.model.ResponseDTO;
import com.ferick.alexander.storage.ContractStorage;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Test;

public class JsonTransformerTest {

    @Test
    public void getContractsTest() throws IOException {
        ContractStorage.clear();
        ContractStorage.add(getContract());

        List<Contract> contracts = ContractStorage.getAll();
        String actualJson = JsonTransformer.toJson(contracts.get(0));

        String expectedJson = Files.lines(
                Paths.get("src/test/resources/contract.json"), StandardCharsets.UTF_8)
                .reduce((s1, s2) -> s1 + s2).orElse("");

        assertEquals(StringUtils.removeSpecialSymbols(expectedJson),
                StringUtils.removeSpecialSymbols(actualJson));
    }

    private Contract getContract() {
        Contract contract = new Contract();

        RequestPath requestPath = new RequestPath();
        requestPath.setPath("/test1/*");
        contract.setRequestPath(requestPath);

        RequestDTO request = new RequestDTO();
        request.setMethod("POST");
        Map<String, String> requestHeaders = new HashMap<>();
        requestHeaders.put("Content-type", "application/json");
        request.setHeaders(requestHeaders);
        request.setBody("{some object}");
        contract.setRequest(request);

        ResponseDTO positiveResponse = new ResponseDTO();
        positiveResponse.setStatus(200);
        Map<String, String> positiveResponseHeaders = new HashMap<>();
        positiveResponseHeaders.put("Content-type", "application/json");
        positiveResponse.setHeaders(positiveResponseHeaders);
        positiveResponse.setBody("{some object}");
        contract.setPositiveResponse(positiveResponse);

        ResponseDTO negativeResponse = new ResponseDTO();
        negativeResponse.setStatus(404);
        Map<String, String> negativeResponseHeaders = new HashMap<>();
        negativeResponseHeaders.put("Content-type", "text/xml");
        negativeResponse.setHeaders(negativeResponseHeaders);
        negativeResponse.setBody("Not found");
        contract.setNegativeResponse(negativeResponse);

        contract.setResponseTimeout(1000);

        return contract;
    }
}