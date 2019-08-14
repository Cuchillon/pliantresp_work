package com.ferick.alexander.utils;

import com.ferick.alexander.model.Contract;
import com.ferick.alexander.model.RequestDTO;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import spark.Request;
import spark.utils.SparkUtils;

public class RequestVerifier {

    public static boolean matchPaths(String requestPath, String contractPath) {

        if (requestPath.equals(contractPath)) {
            return true;
        }

        List<String> requestPathList = SparkUtils.convertRouteToList(requestPath);
        List<String> contractPathList = SparkUtils.convertRouteToList(contractPath);

        int requestPathSize = requestPathList.size();
        int contractPathSize = contractPathList.size();

        if (contractPathSize == requestPathSize) {
            for (int i = 0; i < contractPathSize; i++) {
                String requestPathPart = requestPathList.get(i);
                String contractPathPart = contractPathList.get(i);

                if ((i == contractPathSize - 1) && (contractPathPart.equals("*") && contractPath.endsWith("*"))) {
                    return true;
                }

                if ((!contractPathPart.startsWith(":")
                        && !contractPathPart.equals("*"))
                        && (!contractPathPart.equals(requestPathPart) && !(requestPathPart.startsWith(":") || requestPathPart.equals("*")))
                        || (!requestPathPart.startsWith(":")
                        && !requestPathPart.equals("*"))
                        && (!requestPathPart.equals(contractPathPart) && !(contractPathPart.startsWith(":") || contractPathPart.equals("*")))) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    public static boolean matchRequest(Request request, Contract contract) {
        boolean isMatched = true;
        RequestDTO contractRequest = contract.getRequest();
        if (contractRequest.getMethod().equals(request.requestMethod())) {
            String requestBody = StringUtils.removeSpecialSymbols(request.body());

            if (!requestBody.equals(contractRequest.getBody())) {
                isMatched = false;
            }
            for (Map.Entry<String, String> entry : contractRequest.getHeaders().entrySet()) {
                String headerValue = request.headers(entry.getKey());
                if (headerValue == null || !headerValue.equals(entry.getValue())) {
                    isMatched = false;
                }
            }
        }
        return isMatched;
    }

    public static Map<String, String> getPathParams(String requestPath, String contractPath) {
        Map<String, String> pathParams = new HashMap<>();
        List<String> requestPathList = SparkUtils.convertRouteToList(requestPath);
        List<String> contractPathList = SparkUtils.convertRouteToList(contractPath);

        for (int i = 0; i < contractPathList.size(); i++) {
            String pathPart = contractPathList.get(i);
            if (SparkUtils.isParam(pathPart)) {
                String paramName = pathPart.substring(1);
                pathParams.put(paramName, requestPathList.get(i));
            }
        }

        return pathParams;
    }

    public static String checkPathParams(Map<String, String> pathParams, String bodyTemplate) {
        String responseBody = bodyTemplate.replaceAll("\\s+", "");
        for (Map.Entry<String, String> entry : pathParams.entrySet()) {
            String replacement = String.format("{{%s}}", entry.getKey());
            if (responseBody.contains(replacement)) {
                responseBody = responseBody.replace(replacement, entry.getValue());
            }
        }
        return responseBody;
    }
}