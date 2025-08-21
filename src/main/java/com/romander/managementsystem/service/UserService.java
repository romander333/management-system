package com.romander.managementsystem.service;

import com.romander.managementsystem.dto.user.SignUpRequestDto;
import com.romander.managementsystem.dto.user.UserResponseDto;

public interface UserService {
    UserResponseDto register(SignUpRequestDto signUpRequestDto);
}
