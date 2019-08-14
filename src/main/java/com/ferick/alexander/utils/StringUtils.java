package com.ferick.alexander.utils;

public class StringUtils {

    public static String removeSpecialSymbols(String initialString) {
        return initialString
                .trim()
                .replaceAll("\n", "")
                .replaceAll("\t", "")
                .replaceAll("\\s+", "");
    }
}