package com.ferick.alexander.storage;

import com.ferick.alexander.model.RequestDTO;
import com.ferick.alexander.utils.RequestVerifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RequestStorage {

    private static List<RequestDTO> requests = new ArrayList<>();

    public static Optional<RequestDTO> get(String path, String requestMethod) {
        return requests.stream()
                .filter(request -> RequestVerifier.matchPaths(path, request.getPath()))
                .filter(request -> requestMethod.equals(request.getMethod()))
                .findFirst();
    }

    public static List<RequestDTO> getAll() {
        return requests;
    }

    public static void add(RequestDTO request) {
        for (RequestDTO item : requests) {
            if (RequestVerifier.matchPaths(request.getPath(), item.getPath())
                    && request.getMethod().equals(item.getMethod())) {
                requests.remove(item);
                break;
            }
        }
        requests.add(request);
    }

    public static void clear() {
        requests.clear();
    }

    public static int count() {
        return requests.size();
    }
}
