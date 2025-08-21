package com.romander.managementsystem.mapper;

import com.romander.managementsystem.config.MapperConfig;
import com.romander.managementsystem.dto.user.SignUpRequestDto;
import com.romander.managementsystem.dto.user.UserResponseDto;
import com.romander.managementsystem.model.User;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface UserMapper {
    User toModel(SignUpRequestDto requestDto);

    UserResponseDto toDto(User user);
}
