package com.romander.managementsystem.dto.project;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ProjectRequestDto {
    @NotBlank
    private String name;
    @NotBlank
    private String description;
    @NotNull
    private Long userId;
    @NotNull(message = "Check-in date must not be null")
    private LocalDate startDate;
    @NotNull(message = "Check-in date must not be null")
    private LocalDate endDate;
}
