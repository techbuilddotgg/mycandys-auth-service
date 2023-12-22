package com.feri.sua.auth.config;

import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

public class AnalyticsService {

    public static void saveAnalytics(String endpoint) {
        RestTemplate restTemplate = new RestTemplate();

        String postUrl = "https://mycandys-auth-analytics.onrender.com/analytics";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String requestBody = "{" +
                "\"endpoint\": \"" + endpoint + "\" }";

        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

        restTemplate.exchange(postUrl, HttpMethod.POST, requestEntity, String.class);
    }
}
