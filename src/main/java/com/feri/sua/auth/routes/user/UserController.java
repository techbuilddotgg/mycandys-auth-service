package com.feri.sua.auth.routes.user;

import com.feri.sua.auth.routes.user.dto.RegisterRequestDto;
import com.feri.sua.auth.routes.user.dto.RegisterResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable String id){
        return userService.getUserById(id);
    }

    @PostMapping("/register")
    public RegisterResponseDto registerUser(@RequestBody RegisterRequestDto registerRequestDto){
        return userService.registerUser(registerRequestDto);
    }

    @PostMapping("/login")
    public RegisterResponseDto loginUser(@RequestBody RegisterRequestDto registerRequestDto){
        return userService.loginUser(registerRequestDto);
    }

}

