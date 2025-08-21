package com.romander.managementsystem.controller;

import com.romander.managementsystem.dto.user.SignInRequestDto;
import com.romander.managementsystem.dto.user.SignInResponseDto;
import com.romander.managementsystem.dto.user.SignUpRequestDto;
import com.romander.managementsystem.dto.user.UserResponseDto;
import com.romander.managementsystem.security.AuthenticationService;
import com.romander.managementsystem.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationService authenticationService;
    private final UserService userService;

    @PostMapping("/login")
    public SignInResponseDto login(@RequestBody SignInRequestDto requestDto) {
        return authenticationService.authenticate(requestDto);
    }

    @PostMapping("/register")
    public UserResponseDto register(@RequestBody SignUpRequestDto requestDto) {
        return userService.register(requestDto);
    }
}
