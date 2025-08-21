package com.romander.managementsystem.service;

import com.romander.managementsystem.dto.project.ProjectRequestDto;
import com.romander.managementsystem.dto.project.ProjectResponseDto;
import com.romander.managementsystem.exception.EntityNotFoundException;
import com.romander.managementsystem.mapper.ProjectMapper;
import com.romander.managementsystem.model.Project;
import com.romander.managementsystem.model.User;
import com.romander.managementsystem.repository.ProjectRepository;
import com.romander.managementsystem.security.AuthenticationService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {
    private final ProjectMapper projectMapper;
    private final ProjectRepository projectRepository;
    private final AuthenticationService authenticationService;

    @Override
    public ProjectResponseDto create(ProjectRequestDto requestDto) {
        Project project = projectMapper.toModel(requestDto);
        Project savedProject = projectRepository.save(project);
        return projectMapper.toDto(savedProject);
    }

    @Override
    public List<ProjectResponseDto> getProjectByCurrentUser() {
        User currentUser = authenticationService.getCurrentUser();
        return projectRepository.findAllByUser_Id(currentUser.getId()).stream()
                .map(projectMapper::toDto)
                .toList();
    }

    @Override
    public ProjectResponseDto getProjectById(Long id) {
        Project project = getProject(id);
        return projectMapper.toDto(project);
    }

    @Override
    @Transactional
    public ProjectResponseDto updateProjectById(Long id, ProjectRequestDto requestDto) {
        Project project = getProject(id);
        projectMapper.updateModel(project, requestDto);
        return projectMapper.toDto(project);
    }

    @Override
    public void deleteProjectById(Long id) {
        Project project = getProject(id);
        projectRepository.delete(project);
    }

    private Project getProject(Long id) {
        return projectRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Project not found by id: " + id));
    }
}
