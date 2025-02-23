package com.sintad.blog.service.impl;

import com.sintad.blog.dto.UserDto;
import com.sintad.blog.entity.UserEntity;
import com.sintad.blog.mapper.UserMapper;
import com.sintad.blog.repository.UserRepository;
import com.sintad.blog.service.UserService;
import com.sintad.blog.util.JwtUtil;
import com.sintad.blog.util.exception.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final UserMapper userMapper;

    @Override
    public boolean confirmUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public String login(String username, String password) {
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ValidationException("Invalid username or password", HttpStatus.UNAUTHORIZED));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new ValidationException("Invalid username or password", HttpStatus.UNAUTHORIZED);
        }

        return jwtUtil.generateToken(user.getUsername());
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        if (confirmUsername(userDto.getUsername())) {
            throw new ValidationException("El usuario ya existe", HttpStatus.CONFLICT);
        }
        UserEntity user = UserEntity.builder()
                .username(userDto.getUsername())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .fullname(userDto.getFullname())
                .build();
        UserEntity userCreated = userRepository.save(user);
        userDto.setId(userCreated.getId());
        userDto.setPassword(userCreated.getPassword());
        return userDto;
    }

    @Override
    public UserDto getUserByToken(String tokenWithBearer) {
        String token = tokenWithBearer.substring(7);
        String username = jwtUtil.extractUsername(token);
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ValidationException("Error in token", HttpStatus.UNAUTHORIZED));
        return userMapper.toDto(user);
    }

}