package com.feri.sua.auth.config;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
public class Log {

    private LocalDateTime timestamp;
    private String correlationId;
    private String url;
    private String message;
    private final String service = "mycandys-auth-service";
    private String logType;
}
