package com.ferick.alexander.storage;

import com.ferick.alexander.model.Contract;
import com.ferick.alexander.utils.StringUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ContractStorage {

    private static List<Contract> contracts = new ArrayList<>();

    public static Optional<Contract> get(String pathInfo) {
        return contracts.stream()
                .filter(contract -> StringUtils.matchPaths(pathInfo, contract.getPath()))
                .findFirst();
    }

    public static boolean add(Contract contract) {
        for (Contract item : contracts) {
            if (StringUtils.matchPaths(contract.getPath(), item.getPath())) {
                contracts.remove(item);
                break;
            }
        }
        return contracts.add(contract);
    }

    public static void clear() {
        contracts.clear();
    }

    public static int count() {
        return contracts.size();
    }
}