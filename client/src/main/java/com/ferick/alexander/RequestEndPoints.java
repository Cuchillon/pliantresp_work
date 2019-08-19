package com.ferick.alexander;

import com.ferick.alexander.model.RequestDTO;
import com.ferick.alexander.model.RequestPath;
import feign.RequestLine;
import java.util.List;

public interface RequestEndPoints {

    @RequestLine("GET /storage/requests")
    List<RequestDTO> getRequests();

    @RequestLine("POST /storage/request")
    RequestDTO getRequest(RequestPath requestPath);

    @RequestLine("DELETE /storage/requests")
    String deleteRequests();
}
