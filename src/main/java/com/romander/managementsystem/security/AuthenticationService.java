package com.romander.managementsystem.security;

import com.romander.managementsystem.dto.user.SignInRequestDto;
import com.romander.managementsystem.dto.user.SignInResponseDto;
import com.romander.managementsystem.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final JwtCore jwtCore;

    public SignInResponseDto authenticate(SignInRequestDto signInRequestDto) {
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        signInRequestDto.getEmail(),
                        signInRequestDto.getPassword())
        );
        String token = jwtCore.generateToken(authentication.getName());
        return new SignInResponseDto(token);
    }

    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException("Authentication required");
        }
        return (User) authentication.getPrincipal();
    }
}
