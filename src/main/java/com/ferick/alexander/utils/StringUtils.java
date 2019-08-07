package com.ferick.alexander.utils;

import java.util.List;
import spark.utils.SparkUtils;

public class StringUtils {

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
}