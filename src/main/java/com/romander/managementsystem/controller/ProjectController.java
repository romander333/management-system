package com.romander.managementsystem.controller;

import com.romander.managementsystem.dto.project.ProjectRequestDto;
import com.romander.managementsystem.dto.project.ProjectResponseDto;
import com.romander.managementsystem.service.ProjectService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/projects")
public class ProjectController {
    private final ProjectService projectService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProjectResponseDto createProject(@RequestBody @Valid ProjectRequestDto requestDto) {
        return projectService.create(requestDto);
    }

    @GetMapping
    public List<ProjectResponseDto> getAllProjects() {
        return projectService.getProjectByCurrentUser();
    }

    @GetMapping("/{id}")
    public ProjectResponseDto getProject(@PathVariable Long id) {
        return projectService.getProjectById(id);
    }

    @PutMapping("/{id}")
    public ProjectResponseDto updateProject(@PathVariable Long id,
                                            @RequestBody @Valid ProjectRequestDto requestDto) {
        return projectService.updateProjectById(id, requestDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProject(@PathVariable Long id) {
        projectService.deleteProjectById(id);
    }
}
