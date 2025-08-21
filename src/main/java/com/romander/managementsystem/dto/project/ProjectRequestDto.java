package com.romander.managementsystem.dto.project;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import java.time.LocalDate;
import lombok.Data;

@Data
public class ProjectRequestDto {
    @NotBlank
    private String name;
    @NotBlank
    private String description;
    @NotEmpty
    private LocalDate startDate;
    @NotEmpty
    private LocalDate endDate;
}
