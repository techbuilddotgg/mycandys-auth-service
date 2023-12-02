package com.feri.sua.auth.utils;

import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class PasswordManager extends Argon2PasswordEncoder {

    public PasswordManager() {
        super(16, 32, 1, 60000, 10);
    }
}
