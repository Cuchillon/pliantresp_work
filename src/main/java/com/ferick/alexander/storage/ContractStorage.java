package com.ferick.alexander.storage;

import com.ferick.alexander.model.Contract;
import com.ferick.alexander.model.RequestPath;
import com.ferick.alexander.utils.StringUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ContractStorage {

    private static List<Contract> contracts = new ArrayList<>();

    public static Optional<Contract> get(String pathInfo) {
        return contracts.stream()
                .filter(contract -> StringUtils.matchPaths(pathInfo, contract.getRequestPath().getPath()))
                .findFirst();
    }

    public static List<Contract> getAll() {
        return contracts;
    }

    public static boolean add(Contract contract) {
        delete(contract.getRequestPath());
        return contracts.add(contract);
    }

    public static boolean delete(RequestPath requestPath) {
        boolean deleted = false;
        for (Contract item : contracts) {
            if (StringUtils.matchPaths(requestPath.getPath(), item.getRequestPath().getPath())) {
                deleted = contracts.remove(item);
                break;
            }
        }
        return deleted;
    }

    public static void clear() {
        contracts.clear();
    }

    public static int count() {
        return contracts.size();
    }
}