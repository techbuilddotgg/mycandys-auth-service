package com.feri.sua.auth.user;

import com.feri.sua.auth.user.dto.SaveUserDto;
import com.feri.sua.auth.user.dto.UserByIdResponseDto;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/users")
@SecurityRequirement(name = "Bearer Authentication")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{userId}")
    public UserByIdResponseDto getUserById(@PathVariable("userId") String userId){
        return userService.getUserById(userId);
    }

    @PutMapping("/{userId}")
    public SaveUserDto updateUser(@RequestBody SaveUserDto saveUserDto, @PathVariable("userId") String userId){
        return userService.saveUser(saveUserDto, userId);
    }

    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable("userId") String userId){
        userService.deleteUser(userId);
    }

}

