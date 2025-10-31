package com.example.config;

public class GlobalVariavelToken {
    private static String authToken;

    public static String getAuthToken() {
        return authToken;
    }

    public static void setAuthToken(String token) {
        authToken = token;
    }
}
