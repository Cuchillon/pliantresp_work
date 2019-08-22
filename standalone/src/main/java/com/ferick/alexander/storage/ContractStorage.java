package com.ferick.alexander.storage;

import com.ferick.alexander.model.Contract;
import com.ferick.alexander.model.RequestDTO;
import com.ferick.alexander.utils.RequestVerifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Class to store contracts 'request-response'
 */
public class ContractStorage {

    private static List<Contract> contracts = new ArrayList<>();

    public static Optional<Contract> get(String pathInfo, String requestMethod) {
        return contracts.stream()
                .filter(contract -> RequestVerifier.matchPaths(pathInfo, contract.getRequest().getPath()))
                .filter(contract -> requestMethod.equals(contract.getRequest().getMethod()))
                .findFirst();
    }

    public static List<Contract> getAll() {
        return contracts;
    }

    public static boolean add(Contract contract) {
        delete(contract.getRequest());
        return contracts.add(contract);
    }

    public static boolean delete(RequestDTO request) {
        boolean deleted = false;
        for (Contract contract : contracts) {
            if (RequestVerifier.matchPaths(request.getPath(), contract.getRequest().getPath())
                    && request.getMethod().equals(contract.getRequest().getMethod())) {
                deleted = contracts.remove(contract);
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