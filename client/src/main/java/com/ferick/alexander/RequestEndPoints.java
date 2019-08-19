package com.ferick.alexander;

import com.ferick.alexander.model.RequestDTO;
import feign.RequestLine;
import java.util.List;

public interface RequestEndPoints {

    @RequestLine("GET /storage/requests")
    List<RequestDTO> getRequests();

    @RequestLine("POST /storage/request")
    RequestDTO getRequest(RequestDTO request);

    @RequestLine("DELETE /storage/requests")
    String deleteRequests();
}
