package com.feri.sua.auth.user;

import com.feri.sua.auth.user.dto.UserByIdResponseDto;
import jakarta.servlet.http.HttpServletRequest;
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

    @GetMapping()
    public UserByIdResponseDto getUserById(@RequestAttribute("userId") String userId){
        System.out.println(userId);
        return userService.getUserById(userId);
    }

}

