package com.ferick.alexander.storage;

import com.ferick.alexander.model.RequestDTO;
import com.ferick.alexander.utils.RequestVerifier;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Class to store received requests
 */
public class RequestStorage {

    private static final List<RequestDTO> REQUESTS = Collections.synchronizedList(new ArrayList<>());

    public static Optional<RequestDTO> get(String path, String requestMethod) {
        synchronized (REQUESTS) {
            return REQUESTS.stream()
                    .filter(request -> RequestVerifier.matchPaths(path, request.getPath()))
                    .filter(request -> requestMethod.equals(request.getMethod()))
                    .findFirst();
        }
    }

    public static List<RequestDTO> getAll() {
        return Collections.unmodifiableList(REQUESTS);
    }

    public static void add(RequestDTO request) {
        synchronized (REQUESTS) {
            for (RequestDTO item : REQUESTS) {
                if (RequestVerifier.matchPaths(request.getPath(), item.getPath())
                        && request.getMethod().equals(item.getMethod())) {
                    REQUESTS.remove(item);
                    break;
                }
            }
            REQUESTS.add(request);
        }
    }

    public static void clear() {
        REQUESTS.clear();
    }

    public static int count() {
        return REQUESTS.size();
    }
}
