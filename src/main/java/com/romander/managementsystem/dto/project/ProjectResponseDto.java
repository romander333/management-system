package com.romander.managementsystem.dto.project;

import java.time.LocalDate;

public class ProjectResponseDto {
    private Long id;
    private String name;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private String status;
}
