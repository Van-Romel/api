package com.cronoseuropa.api.utils;

public class Util {

    // As the name suggest... It can be used as a util class
    public static Long sanitizeId(String id) {
        return Long.parseLong(id.replaceAll("[^0-9]", ""));
    }

    private Util() {
    }
}
