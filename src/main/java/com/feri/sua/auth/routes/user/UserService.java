package com.feri.sua.auth.routes.user;

import com.feri.sua.auth.common.exceptions.NotFoundException;
import com.feri.sua.auth.common.exceptions.UnauthorizedException;
import com.feri.sua.auth.routes.user.dto.RegisterRequestDto;
import com.feri.sua.auth.routes.user.dto.RegisterResponseDto;
import com.feri.sua.auth.utils.PasswordManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordManager passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordManager passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User getUserById(String userId) {
        return userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found"));
    }

    public RegisterResponseDto registerUser(RegisterRequestDto registerData) {
        String password = passwordEncoder.encode(registerData.getPassword());
        System.out.println(password + " " + registerData.getEmail());
        User user = new User(registerData.getEmail(), password);
        User savedUser = userRepository.save(user);

        return new RegisterResponseDto(savedUser);
    }

    public RegisterResponseDto loginUser(RegisterRequestDto registerRequestDto) {
        User user = userRepository.findByEmail(registerRequestDto.getEmail()).orElseThrow(() -> new UnauthorizedException("Invalid credentials"));
        if (passwordEncoder.matches(registerRequestDto.getPassword(), user.getPassword())) {
            return new RegisterResponseDto(user);
        } else {
            throw new UnauthorizedException("Invalid credentials");
        }
    }
}
