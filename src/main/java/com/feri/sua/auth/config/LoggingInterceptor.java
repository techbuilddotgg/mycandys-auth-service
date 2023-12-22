package com.feri.sua.auth.config;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.feri.sua.auth.rabbitmq.MessageProducer;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
@AllArgsConstructor
public class LoggingInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(LoggingInterceptor.class);
    private final MessageProducer messageProducer;
    private final ObjectMapper objectMapper;

    @Override
    public void postHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler, @Nullable ModelAndView modelAndView) throws Exception{

        MDC.put("AppName", "mycandys-auth-service");
        MDC.put("URL", request.getRequestURL().toString());
        MDC.put("Colleration", request.getHeader("X-Correlation-ID"));

        if (response.getStatus() == 200) {
            MDC.put("LogType", "Info");
            logger.info("Response: {} {}", response.getStatus(), "OK");
        } else {
            MDC.put("LogType", "Error");
            logger.info("Response: {} {}", response.getStatus(), "ERROR");
        }

        messageProducer.sendToLogQueue(objectMapper.writeValueAsString(new Log(LocalDateTime.now(), request.getHeader("X-Correlation-ID"), request.getRequestURL().toString(), response.getStatus() == 200 ? "OK" : "ERROR", MDC.get("LogType"))));
        MDC.clear();

        AnalyticsService.saveAnalytics(request.getRequestURL().toString());

        response.setHeader("X-Correlation-ID", UUID.randomUUID().toString());
    }
}

