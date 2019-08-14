package com.ferick.alexander.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ferick.alexander.model.RequestDTO;
import com.ferick.alexander.model.RequestPath;
import com.ferick.alexander.storage.RequestStorage;
import com.ferick.alexander.utils.JsonTransformer;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import org.eclipse.jetty.http.HttpStatus;
import spark.Request;
import spark.Response;

public class RequestService {

    public String getRequest(Request request, Response response) {
        String responseBody = "";
        RequestPath requestPath = null;

        try {
            requestPath = new ObjectMapper().readValue(request.body(), RequestPath.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (requestPath != null) {
            Optional<RequestDTO> requestOptional = RequestStorage.get(requestPath.getPath());

            if (requestOptional.isPresent()) {
                RequestDTO requestDTO = requestOptional.get();
                response.status(HttpStatus.OK_200);
                response.type("application/json");
                responseBody = JsonTransformer.toJson(requestDTO);
            } else {
                response.status(HttpStatus.NOT_FOUND_404);
                responseBody = "Requests list is empty";
            }
        } else {
            response.status(HttpStatus.BAD_REQUEST_400);
        }

        return responseBody;
    }

    public String getRequests(Request request, Response response) {
        List<String> jsonList = new ArrayList<>();
        Collection<RequestDTO> requests = RequestStorage.getAll();

        if (!requests.isEmpty()) {
            response.status(HttpStatus.OK_200);
            response.type("application/json");
            for (RequestDTO requestDTO : requests) {
                jsonList.add(JsonTransformer.toJson(requestDTO));
            }
            return jsonList.toString();
        } else {
            response.status(HttpStatus.NOT_FOUND_404);
            return "Requests list is empty";
        }
    }

    public String deleteRequests(Request request, Response response) {
        String responseBody;

        RequestStorage.clear();

        if ((RequestStorage.count() == 0)) {
            response.status(HttpStatus.OK_200);
            responseBody = "Requests list is empty";
        } else {
            response.status(HttpStatus.INTERNAL_SERVER_ERROR_500);
            responseBody = "Requests list is not empty";
        }

        return responseBody;
    }
}
