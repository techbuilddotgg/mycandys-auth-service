package com.feri.sua.auth.user;

import com.feri.sua.auth.common.exceptions.NotFoundException;
import com.feri.sua.auth.common.exceptions.UnauthorizedException;
import com.feri.sua.auth.user.dto.EmailListDto;
import com.feri.sua.auth.user.dto.SaveUserDto;
import com.feri.sua.auth.user.dto.UserByIdResponseDto;
import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final MongoTemplate mongoTemplate;

    public UserByIdResponseDto getUserById(String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found"));
        User jwtUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        System.out.println(jwtUser.getId() + " " + userId);
        if(jwtUser.getRole() != Role.ADMIN && !jwtUser.getId().equals(userId)){
            throw new UnauthorizedException("You are not authorized to access this resource");
        }

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

    public EmailListDto getUsersEmails() {
        List<User> users = userRepository.findAll();
        return EmailListDto.builder().emails(users.stream().map(User::getEmail).toList()).build();

    }
}
