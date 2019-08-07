package com.ferick.alexander.storage;

import static org.junit.Assert.*;

import com.ferick.alexander.model.Contract;
import org.junit.Test;

public class ContractStorageTest {

    @Test
    public void withSplatStorageTest() {
        ContractStorage.clear();

        Contract contract = new Contract();
        contract.setPath("/test1/*/level1/*");

        ContractStorage.add(contract);
        assertFalse(ContractStorage.get("/test").isPresent());
        assertFalse(ContractStorage.get("/test1/param1/level2/*").isPresent());
        assertTrue(ContractStorage.get("/test1/param/level1/*").isPresent());
        assertTrue(ContractStorage.get("/test1/*/level1/param").isPresent());
        assertTrue(ContractStorage.get("/test1/:param1/level1/:param2").isPresent());
    }

    @Test
    public void withParamsStorageTest() {
        ContractStorage.clear();

        Contract contract = new Contract();
        contract.setPath("/test1/:param1/level1/:param2");

        ContractStorage.add(contract);
        assertFalse(ContractStorage.get("/test/value").isPresent());
        assertFalse(ContractStorage.get("/test1/value/level2/*").isPresent());
        assertTrue(ContractStorage.get("/test1/value/level1/value2").isPresent());
        assertTrue(ContractStorage.get("/test1/*/level1/*").isPresent());
        assertTrue(ContractStorage.get("/test1/:param2/level1/:param1").isPresent());
    }

    @Test
    public void withoutParamsStorageTest() {
        ContractStorage.clear();

        Contract contract = new Contract();
        contract.setPath("/test1/value1/level1/value2");

        ContractStorage.add(contract);
        assertFalse(ContractStorage.get("/test").isPresent());
        assertFalse(ContractStorage.get("/test1/param1/level2/*").isPresent());
        assertTrue(ContractStorage.get("/test1/:param/level1/:param2").isPresent());
    }

    @Test
    public void changePathStorageTest() {
        ContractStorage.clear();

        Contract contract1 = new Contract();
        contract1.setPath("/test1/*");
        ContractStorage.add(contract1);

        Contract contract2 = new Contract();
        contract2.setPath("/test2/*");
        ContractStorage.add(contract2);

        assertEquals(2, ContractStorage.count());

        Contract contract3 = new Contract();
        contract3.setPath("/test1/:param");
        ContractStorage.add(contract3);

        assertEquals(2, ContractStorage.count());

        Contract contract4 = new Contract();
        contract4.setPath("/test1/*");
        ContractStorage.add(contract4);

        assertEquals(2, ContractStorage.count());

        Contract contract5 = new Contract();
        contract5.setPath("/test2/value");
        ContractStorage.add(contract5);

        assertEquals(2, ContractStorage.count());

        Contract contract6 = new Contract();
        contract6.setPath("/test2/:param");
        ContractStorage.add(contract6);

        assertEquals(2, ContractStorage.count());
    }
}