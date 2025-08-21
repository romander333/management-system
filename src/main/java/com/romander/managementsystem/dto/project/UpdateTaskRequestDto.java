package com.romander.managementsystem.dto.project;

import java.time.LocalDate;
import lombok.Data;

@Data
public class UpdateTaskRequestDto {
    private String name;
    private String description;
    private String priority;
    private String status;
    private LocalDate dueDate;
}
