package com.romander.managementsystem.validation;

import com.romander.managementsystem.dto.SignUpRequestDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<Password, Object> {
    @Override
    public boolean isValid(Object object, ConstraintValidatorContext constraintValidatorContext) {
        if (object instanceof SignUpRequestDto requestDto) {
            return requestDto.getPassword().equals(requestDto.getConfirmPassword());
        }
        return false;
    }
}
