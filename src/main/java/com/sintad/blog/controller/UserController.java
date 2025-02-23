package com.sintad.blog.controller;

import com.sintad.blog.dto.UserDto;
import com.sintad.blog.service.UserService;
import com.sintad.blog.util.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/confirm-username/{username}")
    public ApiResponse<Boolean> confirmUsername(@PathVariable String username) {
        boolean exists = userService.confirmUsername(username);
        return ApiResponse.success(exists);
    }

    @PostMapping("/login")
    public ApiResponse<String> login(@RequestBody UserDto userDto) {
        String token = userService.login(userDto.getUsername(), userDto.getPassword());
        return ApiResponse.success(token, "Login successful");
    }

    @PostMapping("/signup")
    public ApiResponse<UserDto> signup(@RequestBody UserDto userDto) {
        UserDto user = userService.createUser(userDto);
        return ApiResponse.success(user);
    }

    @GetMapping("/me")
    public ApiResponse<UserDto> getUserByToken(@RequestHeader("Authorization") String token) {
        UserDto response = userService.getUserByToken(token);
        return ApiResponse.success(response);
    }

}