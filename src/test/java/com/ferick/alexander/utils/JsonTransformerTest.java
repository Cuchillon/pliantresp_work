package com.ferick.alexander.utils;

import static org.junit.Assert.*;

import com.ferick.alexander.model.Contract;
import com.ferick.alexander.model.RequestDTO;
import com.ferick.alexander.model.RequestPath;
import com.ferick.alexander.model.ResponseDTO;
import com.ferick.alexander.storage.ContractStorage;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Test;

public class JsonTransformerTest {

    private static final String JSON = "{\n" +
            "  \"request\" : {\n" +
            "    \"method\" : \"POST\",\n" +
            "    \"body\" : \"{some object}\",\n" +
            "    \"headers\" : {\n" +
            "      \"Content-type\" : \"application/json\"\n" +
            "    }\n" +
            "  },\n" +
            "  \"request_path\" : {\n" +
            "    \"path\" : \"/test1/*\"\n" +
            "  },\n" +
            "  \"positive_response\" : {\n" +
            "    \"status\" : 200,\n" +
            "    \"body\" : \"{some object}\",\n" +
            "    \"headers\" : {\n" +
            "      \"Content-type\" : \"application/json\"\n" +
            "    }\n" +
            "  },\n" +
            "  \"negative_response\" : {\n" +
            "    \"status\" : 404,\n" +
            "    \"body\" : \"{some object}\",\n" +
            "    \"headers\" : {\n" +
            "      \"Content-type\" : \"application/json\"\n" +
            "    }\n" +
            "  },\n" +
            "  \"response_timeout\" : 1000\n" +
            "}";

    @Test
    public void getContractsTest() {
        ContractStorage.clear();
        ContractStorage.add(getContract());

        List<Contract> contracts = ContractStorage.getAll();
        String json = JsonTransformer.toJson(contracts.get(0));

        assertEquals(JSON, json);
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
        negativeResponseHeaders.put("Content-type", "application/json");
        negativeResponse.setHeaders(negativeResponseHeaders);
        negativeResponse.setBody("{some object}");
        contract.setNegativeResponse(negativeResponse);

        contract.setResponseTimeout(1000);

        return contract;
    }
}