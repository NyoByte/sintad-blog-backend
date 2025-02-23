package com.sintad.blog.service;

import com.sintad.blog.dto.UserDto;

public interface UserService {

    boolean confirmUsername(String username);

    String login(String username, String password);

    UserDto createUser(UserDto userDto);

    UserDto getUserByToken(String token);

}