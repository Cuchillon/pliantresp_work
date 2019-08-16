package com.ferick.alexander.utils;

import java.util.HashMap;
import java.util.Map;

public class MapUtils {

    public static Map<String, String> asMap(String... entries) {
        if (entries.length % 2 != 0) {
            throw new IllegalArgumentException("Odd number of elements in key-value pairs!");
        }

        Map<String, String> map = new HashMap<>();

        for (int i = 0; i < entries.length; i += 2) {
            map.put(entries[i], entries[i + 1]);
        }

        return map;
    }
}
