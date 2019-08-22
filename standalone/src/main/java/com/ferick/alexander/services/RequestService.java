package com.ferick.alexander.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ferick.alexander.model.RequestDTO;
import com.ferick.alexander.storage.RequestStorage;
import com.ferick.alexander.utils.JsonTransformer;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.eclipse.jetty.http.HttpStatus;
import spark.Request;
import spark.Response;

public class RequestService {

    /**
     * Method gets received request stored on {@link RequestStorage}
     * matching path and request method
     *
     * @param request
     * @param response
     * @return response body containing request json or message that
     * there is no suitable request in the storage
     */
    public String getRequest(Request request, Response response) {
        String responseBody = "";
        RequestDTO requestDTO = null;

        try {
            requestDTO = new ObjectMapper().readValue(request.body(), RequestDTO.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (requestDTO != null) {
            Optional<RequestDTO> requestOptional = RequestStorage.get(requestDTO.getPath(), requestDTO.getMethod());
            response.status(HttpStatus.OK_200);

            if (requestOptional.isPresent()) {
                RequestDTO storedRequest = requestOptional.get();
                response.type("application/json");
                responseBody = JsonTransformer.toJson(storedRequest);
            } else {
                responseBody = "Request list does not contain any suitable request";
            }
        } else {
            response.status(HttpStatus.BAD_REQUEST_400);
        }

        return responseBody;
    }

    /**
     * Method gets all received requests stored on {@link RequestStorage}
     *
     * @param request
     * @param response
     * @return list of stored requests, if there are no requests returns empty list
     */
    public String getRequests(Request request, Response response) {
        List<String> jsonList = new ArrayList<>();
        List<RequestDTO> requests = RequestStorage.getAll();

        response.status(HttpStatus.OK_200);
        response.type("application/json");
        for (RequestDTO requestDTO : requests) {
            jsonList.add(JsonTransformer.toJson(requestDTO));
        }

        return jsonList.toString();
    }

    /**
     * Method clears request list
     *
     * @param request
     * @param response
     * @return response body containing message whether request list is cleared or not
     */
    public String deleteRequests(Request request, Response response) {
        String responseBody;

        RequestStorage.clear();

        if ((RequestStorage.count() == 0)) {
            response.status(HttpStatus.OK_200);
            responseBody = "Request list is empty";
        } else {
            response.status(HttpStatus.INTERNAL_SERVER_ERROR_500);
            responseBody = "Request list is not empty";
        }

        return responseBody;
    }
}
