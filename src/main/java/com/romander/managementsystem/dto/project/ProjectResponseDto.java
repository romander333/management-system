package com.romander.managementsystem.dto.project;

import com.romander.managementsystem.model.Project;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDate;

@Data
@Accessors(chain = true)
public class ProjectResponseDto {
    private Long id;
    private String name;
    private String description;
    private Long userId;
    private LocalDate startDate;
    private LocalDate endDate;
    private Project.Status status;
}
