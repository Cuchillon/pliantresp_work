package com.ferick.alexander.storage;

import static org.junit.Assert.*;

import com.ferick.alexander.model.Contract;
import com.ferick.alexander.model.RequestPath;
import org.junit.Test;

public class ContractStorageTest {

    @Test
    public void withSplatMatchingTest() {
        ContractStorage.clear();

        ContractStorage.add(getContractWithPath("/test1/*/level1/*"));
        assertFalse(ContractStorage.get("/test").isPresent());
        assertFalse(ContractStorage.get("/test1/param1/level2/*").isPresent());
        assertTrue(ContractStorage.get("/test1/param/level1/*").isPresent());
        assertTrue(ContractStorage.get("/test1/*/level1/param").isPresent());
        assertTrue(ContractStorage.get("/test1/:param1/level1/:param2").isPresent());
    }

    @Test
    public void withParamsMatchingTest() {
        ContractStorage.clear();

        ContractStorage.add(getContractWithPath("/test1/:param1/level1/:param2"));
        assertFalse(ContractStorage.get("/test/value").isPresent());
        assertFalse(ContractStorage.get("/test1/value/level2/*").isPresent());
        assertTrue(ContractStorage.get("/test1/value/level1/value2").isPresent());
        assertTrue(ContractStorage.get("/test1/*/level1/*").isPresent());
        assertTrue(ContractStorage.get("/test1/:param2/level1/:param1").isPresent());
    }

    @Test
    public void withoutParamsMatchingTest() {
        ContractStorage.clear();

        ContractStorage.add(getContractWithPath("/test1/value1/level1/value2"));
        assertFalse(ContractStorage.get("/test").isPresent());
        assertFalse(ContractStorage.get("/test1/param1/level2/*").isPresent());
        assertTrue(ContractStorage.get("/test1/:param/level1/:param2").isPresent());
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

    private Contract getContractWithPath(String path) {
        Contract contract = new Contract();
        RequestPath requestPath = new RequestPath();
        requestPath.setPath(path);
        contract.setRequestPath(requestPath);
        return contract;
    }
}