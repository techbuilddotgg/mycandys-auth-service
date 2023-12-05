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

    @GetMapping()
    public UserByIdResponseDto getUserById(@RequestAttribute("userId") String userId){
        System.out.println(userId);
        return userService.getUserById(userId);
    }

    @PostMapping()
    public SaveUserDto saveUser(@RequestBody SaveUserDto saveUserDto, @RequestAttribute("userId") String userId){
        return userService.saveUser(saveUserDto, userId);
    }

    @PutMapping()
    public SaveUserDto updateUser(@RequestBody SaveUserDto saveUserDto, @RequestAttribute("userId") String userId){
        return userService.saveUser(saveUserDto, userId);
    }

    @DeleteMapping()
    public void deleteUser(@RequestAttribute("userId") String userId){
        userService.deleteUser(userId);
    }

}

