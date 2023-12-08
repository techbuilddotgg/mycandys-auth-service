package com.feri.sua.auth.user;

import com.feri.sua.auth.common.exceptions.NotFoundException;
import com.feri.sua.auth.user.dto.SaveUserDto;
import com.feri.sua.auth.user.dto.UserByIdResponseDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserByIdResponseDto getUserById(String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found"));
        return UserByIdResponseDto.builder().id(user.getId()).email(user.getEmail()).name(user.getName()).address(user.getAddress()).city(user.getCity()).country(user.getCountry()).postalCode(user.getPostalCode()).phone(user.getPhone()).role(user.getRole()).build();
    }

    public SaveUserDto saveUser(SaveUserDto saveUserDto, String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found"));
        user.setName(saveUserDto.getName());
        user.setAddress(saveUserDto.getAddress());
        user.setPhone(saveUserDto.getPhone());
        user.setCity(saveUserDto.getCity());
        user.setCountry(saveUserDto.getCountry());
        user.setPostalCode(saveUserDto.getPostalCode());
        userRepository.save(user);
        saveUserDto.setId(user.getId());
        saveUserDto.setEmail(user.getEmail());
        return saveUserDto;
    }

    public void deleteUser(String userId) {
        userRepository.deleteById(userId);
    }
}
