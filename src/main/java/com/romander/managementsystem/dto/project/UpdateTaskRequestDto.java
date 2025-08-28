package com.romander.managementsystem.dto.project;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateTaskRequestDto {
    @NotBlank
    private String name;
    @NotBlank
    private String description;
    @NotBlank
    private String priority;
    @NotBlank
    private String status;
    @NotNull(message = "Check-in date must not be null")
    private LocalDate dueDate;
}
