package com.romander.managementsystem.dto;

import com.romander.managementsystem.validation.Password;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
@Password
public class SignUpRequestDto {
    @Email
    private String email;
    @Length(min = 8, max = 32)
    private String password;
    @Length(min = 8, max = 32)
    private String confirmPassword;
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
}
