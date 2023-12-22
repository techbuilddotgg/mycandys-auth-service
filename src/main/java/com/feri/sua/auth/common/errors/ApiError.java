package com.feri.sua.auth.common.errors;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public class ApiError {

    private HttpStatus status;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime timestamp;
    private String message;

    private ApiError() {
        timestamp = LocalDateTime.now();
    }

    public ApiError(HttpStatus status, String message) {
        this();
        this.status = status;
        this.message = message;
    }

    public String serializeToJson() {
        return "{" +
                "\"status\": \"" + status + "\"" +
                ", \"timestamp\": \"" + timestamp + "\"" +
                ", \"message\": \"" + message + "\"" +
                '}';
    }
}
