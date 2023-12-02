package com.feri.sua.auth.routes.user.dto;

import com.feri.sua.auth.routes.user.User;

public class RegisterResponseDto {

    private String email;

    public RegisterResponseDto(User user) {
        this.email = user.getEmail();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
