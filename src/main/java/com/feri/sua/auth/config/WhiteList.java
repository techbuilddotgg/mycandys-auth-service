package com.feri.sua.auth.config;

public class WhiteList {

    public static final String[] URLS = {"/health", "/auth/**",
            "/v2/api-docs",
            "/v3/api-docs",
            "/v3/api-docs/**",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui/**",
            "/webjars/**",
            "/swagger-ui.html"};

    public static boolean isPresent(String targetUrl) {
        for (String url : URLS) {
            if (url.endsWith("/**")) {
                String patternPrefix = url.substring(0, url.length() - 3);
                if (targetUrl.startsWith(patternPrefix)) {
                    return true;
                }
            } else {
                if (url.equals(targetUrl)) {
                    return true;
                }
            }
        }
        return false;
    }
}
