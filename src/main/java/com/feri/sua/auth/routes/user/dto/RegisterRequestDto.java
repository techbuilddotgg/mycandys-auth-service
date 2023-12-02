package com.feri.sua.auth.routes.user.dto;

public class RegisterRequestDto {

        private String email;
        private String password;

        public RegisterRequestDto() {
        }

        public RegisterRequestDto(String email, String password) {
            this.email = email;
            this.password = password;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String userId) {
            this.email = userId;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
}
