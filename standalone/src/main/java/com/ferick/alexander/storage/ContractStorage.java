package com.ferick.alexander.storage;

import com.ferick.alexander.model.Contract;
import com.ferick.alexander.model.RequestDTO;
import com.ferick.alexander.utils.RequestVerifier;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Class to store contracts 'request-response'
 */
public class ContractStorage {

    private static final List<Contract> CONTRACTS = Collections.synchronizedList(new ArrayList<>());

    public static Optional<Contract> get(String pathInfo, String requestMethod) {
        synchronized (CONTRACTS) {
            return CONTRACTS.stream()
                    .filter(contract -> RequestVerifier.matchPaths(pathInfo, contract.getRequest().getPath()))
                    .filter(contract -> requestMethod.equals(contract.getRequest().getMethod()))
                    .findFirst();
        }
    }

    public static List<Contract> getAll() {
        return Collections.unmodifiableList(CONTRACTS);
    }

    public static boolean add(Contract contract) {
        RequestDTO request = contract.getRequest();
        synchronized (CONTRACTS) {
            delete(request);
            return CONTRACTS.add(contract);
        }
    }

    public static boolean delete(RequestDTO request) {
        boolean deleted = false;
        synchronized (CONTRACTS) {
            for (Contract contract : CONTRACTS) {
                if (RequestVerifier.matchPaths(request.getPath(), contract.getRequest().getPath())
                        && request.getMethod().equals(contract.getRequest().getMethod())) {
                    deleted = CONTRACTS.remove(contract);
                    break;
                }
            }
        }
        return deleted;
    }

    public static void clear() {
        CONTRACTS.clear();
    }

    public static int count() {
        return CONTRACTS.size();
    }
}