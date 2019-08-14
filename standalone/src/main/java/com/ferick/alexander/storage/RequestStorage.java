package com.ferick.alexander.storage;

import com.ferick.alexander.model.RequestDTO;
import com.ferick.alexander.utils.RequestVerifier;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class RequestStorage {

    private static Map<String, RequestDTO> requests = new HashMap<>();

    public static Optional<RequestDTO> get(String path) {
        return requests.entrySet().stream()
                .filter(entry -> RequestVerifier.matchPaths(path, entry.getKey()))
                .map(Map.Entry::getValue)
                .findFirst();
    }

    public static Collection<RequestDTO> getAll() {
        return requests.values();
    }

    public static void add(String path, RequestDTO request) {
        for (Map.Entry<String, RequestDTO> entry : requests.entrySet()) {
            if (RequestVerifier.matchPaths(path, entry.getKey())) {
                requests.remove(entry.getKey());
                break;
            }
        }
        requests.put(path, request);
    }

    public static void clear() {
        requests.clear();
    }

    public static int count() {
        return requests.size();
    }
}
