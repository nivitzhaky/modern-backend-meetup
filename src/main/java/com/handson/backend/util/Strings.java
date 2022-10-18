package com.handson.backend.util;

public class Strings {

    public static String likeLowerOrNull(String str) {
        return str != null ? "%" + str.toLowerCase() + "%" : null;
    }
}
