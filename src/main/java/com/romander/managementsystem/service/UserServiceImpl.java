package com.romander.managementsystem.service;

import com.romander.managementsystem.dto.user.SignUpRequestDto;
import com.romander.managementsystem.dto.user.UserResponseDto;
import com.romander.managementsystem.exception.RegistrationException;
import com.romander.managementsystem.mapper.UserMapper;
import com.romander.managementsystem.model.Role;
import com.romander.managementsystem.model.User;
import com.romander.managementsystem.repository.RoleRepository;
import com.romander.managementsystem.repository.UserRepository;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final RoleRepository roleRepository;

    @Override
    public UserResponseDto register(SignUpRequestDto signUpRequestDto) {
        if (userRepository.existsByEmail(signUpRequestDto.getEmail())) {
            throw new RegistrationException(signUpRequestDto.getEmail()
                    + " :Email already exists");
        }
        User user = userMapper.toModel(signUpRequestDto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Role role = roleRepository.findByName(Role.RoleName.USER)
                .orElseThrow(() -> new RegistrationException("Role not found by name"
                        + Role.RoleName.USER));
        user.setRoles(Set.of(role));
        User savedUser = userRepository.save(user);
        return userMapper.toDto(savedUser);
    }
}
