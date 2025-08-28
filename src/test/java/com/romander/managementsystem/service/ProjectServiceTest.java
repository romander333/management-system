package com.romander.managementsystem.service;

import com.romander.managementsystem.dto.project.ProjectRequestDto;
import com.romander.managementsystem.dto.project.ProjectResponseDto;
import com.romander.managementsystem.exception.EntityNotFoundException;
import com.romander.managementsystem.mapper.ProjectMapper;
import com.romander.managementsystem.model.Project;
import com.romander.managementsystem.model.User;
import com.romander.managementsystem.repository.ProjectRepository;
import com.romander.managementsystem.security.AuthenticationService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.testcontainers.shaded.org.apache.commons.lang3.builder.EqualsBuilder;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.romander.managementsystem.util.ProjectDataTest.*;
import static com.romander.managementsystem.util.UserDataTest.getSampleUser;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProjectServiceTest {
    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private ProjectMapper projectMapper;

    @Mock
    private AuthenticationService authenticationService;

    @InjectMocks
    private ProjectServiceImpl projectService;

    @Test
    void create_WithValidRequest_ShouldCreateAndReturnDto() {
        Project project = getSampleProject();
        ProjectResponseDto expected = getSampleProjectResponseDto();
        ProjectRequestDto requestDto = getSampleProjectRequestDto();

        when(projectRepository.save(project)).thenReturn(project);
        when(projectMapper.toDto(project)).thenReturn(expected);
        when(projectMapper.toModel(requestDto)).thenReturn(project);
        
        ProjectResponseDto actual = projectService.create(requestDto);

        assertEquals(expected, actual);
    }

    @Test
    void getProjectByCurrentUser_WithAuthenticatedUser_ShouldReturnDto() {
        Project project = getSampleProject();
        Project anotherProject = getSampleAnotherProject();
        List<Project> projects = Arrays.asList(project, anotherProject);
        ProjectResponseDto expected1 = getSampleProjectResponseDto();
        ProjectResponseDto expected2 = getSampleAnotherProjectResponseDto();
        List<ProjectResponseDto> expectedList = Arrays.asList(expected1, expected2);
        User currentUser = getSampleUser();

        when(authenticationService.getCurrentUser()).thenReturn(currentUser);
        when(projectMapper.toDto(project)).thenReturn(expected1);
        when(projectMapper.toDto(anotherProject)).thenReturn(expected2);
        when(projectRepository.findAllByUser_Id(currentUser.getId())).thenReturn(projects);

        List<ProjectResponseDto> actual = projectService.getProjectByCurrentUser();

        for (int i = 0; i < expectedList.size(); i++) {
            assertEquals(expectedList.get(i), actual.get(i));
        }
    }

    @Test
    void getProjectById_WithValidId_ShouldReturnDto() {
        Project project = getSampleProject();
        ProjectResponseDto expected = getSampleProjectResponseDto();

        when(projectRepository.findById(project.getId())).thenReturn(Optional.of(project));
        when(projectMapper.toDto(project)).thenReturn(expected);

        ProjectResponseDto actual = projectService.getProjectById(project.getId());

        assertEquals(expected, actual);
    }

    @Test
    void getProjectById_WithInValidId_ShouldReturnDto() {
        Long id = -10L;

        Exception exception = Assertions.assertThrows(EntityNotFoundException.class,
                () -> projectService.getProjectById(id));

        assertEquals("Project not found by id: " + id, exception.getMessage());
    }

    @Test
    void updateProjectById_WithValidRequest_ShouldReturnDto() {
        Project project = getSampleAnotherProject();
        ProjectResponseDto expected = getSampleProjectResponseDto();
        ProjectRequestDto requestDto = getSampleProjectRequestDto();

        doNothing().when(projectMapper).updateModel(project, requestDto);
        when(projectMapper.toDto(project)).thenReturn(expected);
        when(projectRepository.findById(project.getId())).thenReturn(Optional.of(project));

        ProjectResponseDto actual = projectService.updateProjectById(project.getId(), requestDto);

        assertThat(actual)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(expected);
    }

    @Test
    void deleteProjectById_WithValidId_ShouldDelete() {
        Project project = getSampleProject();

        when(projectRepository.findById(project.getId())).thenReturn(Optional.of(project));

        projectService.deleteProjectById(project.getId());
    }
}
