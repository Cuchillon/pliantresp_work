package com.ferick.alexander.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.ferick.alexander.model.Contract;
import com.ferick.alexander.model.RequestDTO;
import org.junit.Test;

public class ContractStorageTest {

    private static final String REQUEST_METHOD = "POST";
    private static final String WRONG_REQUEST_METHOD = "GET";

    @Test
    public void withSplatMatchingTest() {
        ContractStorage.clear();

        ContractStorage.add(getContractWithPath("/test1/*/level1/*"));
        assertFalse(ContractStorage.get("/test", REQUEST_METHOD).isPresent());
        assertFalse(ContractStorage.get("/test1/param1/level2/*", REQUEST_METHOD).isPresent());
        assertTrue(ContractStorage.get("/test1/param/level1/*", REQUEST_METHOD).isPresent());
        assertTrue(ContractStorage.get("/test1/*/level1/param", REQUEST_METHOD).isPresent());
        assertTrue(ContractStorage.get("/test1/:param1/level1/:param2", REQUEST_METHOD).isPresent());
    }

    @Test
    public void withParamsMatchingTest() {
        ContractStorage.clear();

        ContractStorage.add(getContractWithPath("/test1/:param1/level1/:param2"));
        assertFalse(ContractStorage.get("/test/value", REQUEST_METHOD).isPresent());
        assertFalse(ContractStorage.get("/test1/value/level2/*", REQUEST_METHOD).isPresent());
        assertTrue(ContractStorage.get("/test1/value/level1/value2", REQUEST_METHOD).isPresent());
        assertTrue(ContractStorage.get("/test1/*/level1/*", REQUEST_METHOD).isPresent());
        assertTrue(ContractStorage.get("/test1/:param2/level1/:param1", REQUEST_METHOD).isPresent());
    }

    @Test
    public void withoutParamsMatchingTest() {
        ContractStorage.clear();

        ContractStorage.add(getContractWithPath("/test1/value1/level1/value2"));
        assertFalse(ContractStorage.get("/test", REQUEST_METHOD).isPresent());
        assertFalse(ContractStorage.get("/test1/param1/level2/*", REQUEST_METHOD).isPresent());
        assertTrue(ContractStorage.get("/test1/:param/level1/:param2", REQUEST_METHOD).isPresent());
    }

    @Test
    public void changePathStorageTest() {
        ContractStorage.clear();

        ContractStorage.add(getContractWithPath("/test1/*"));
        ContractStorage.add(getContractWithPath("/test2/*"));
        assertEquals(2, ContractStorage.count());

        ContractStorage.add(getContractWithPath("/test1/:param"));
        assertEquals(2, ContractStorage.count());

        ContractStorage.add(getContractWithPath("/test1/*"));
        assertEquals(2, ContractStorage.count());

        ContractStorage.add(getContractWithPath("/test2/value"));
        assertEquals(2, ContractStorage.count());

        ContractStorage.add(getContractWithPath("/test2/:param"));
        assertEquals(2, ContractStorage.count());
    }

    @Test
    public void withWrongMethodTest() {
        ContractStorage.clear();

        ContractStorage.add(getContractWithPath("/test1/value1"));
        assertFalse(ContractStorage.get("/test1/value1", WRONG_REQUEST_METHOD).isPresent());
        assertTrue(ContractStorage.get("/test1/value1", REQUEST_METHOD).isPresent());
    }

    private Contract getContractWithPath(String path) {
        Contract contract = new Contract();
        RequestDTO request = new RequestDTO();
        request.setPath(path);
        request.setMethod(REQUEST_METHOD);
        contract.setRequest(request);
        return contract;
    }
}