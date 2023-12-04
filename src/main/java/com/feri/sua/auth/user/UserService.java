package com.feri.sua.auth.user;

import com.feri.sua.auth.common.exceptions.NotFoundException;
import com.feri.sua.auth.user.dto.UserByIdResponseDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserByIdResponseDto getUserById(String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found"));
        return UserByIdResponseDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }
}
