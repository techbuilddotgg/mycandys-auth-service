package com.feri.sua.auth.config;

import com.feri.sua.auth.user.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class UserInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String userId = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
        request.setAttribute("userId", userId);
        return true;
    }
}
