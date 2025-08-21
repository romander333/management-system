package com.romander.managementsystem.service;

import com.romander.managementsystem.dto.project.ProjectRequestDto;
import com.romander.managementsystem.dto.project.ProjectResponseDto;
import java.util.List;

public interface ProjectService {
    ProjectResponseDto create(ProjectRequestDto requestDto);

    List<ProjectResponseDto> getProjectByCurrentUser();

    ProjectResponseDto getProjectById(Long id);

    ProjectResponseDto updateProjectById(Long id, ProjectRequestDto requestDto);

    void deleteProjectById(Long id);
}
