package com.romander.managementsystem.util;

import com.romander.managementsystem.dto.project.ProjectRequestDto;
import com.romander.managementsystem.dto.project.ProjectResponseDto;
import com.romander.managementsystem.model.Project;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.romander.managementsystem.util.UserDataTest.getSampleUser;

public class ProjectDataTest {
    public static Project getSampleProject() {
        Project project = new Project();
        project.setId(1L);
        project.setName("management");
        project.setDescription("big project");
        project.setStartDate(LocalDate.of(2025, 10, 5));
        project.setEndDate(LocalDate.of(2025, 11, 1));
        project.setStatus(Project.Status.INITIATED);
        project.setUser(getSampleUser());

        return project;
    }

    public static Project getSampleAnotherProject() {
        Project project2 = new Project();
        project2.setId(2L);
        project2.setName("marketing");
        project2.setDescription("small project");
        project2.setStartDate(LocalDate.of(2005, 10, 5));
        project2.setEndDate(LocalDate.of(2005, 11, 1));
        project2.setStatus(Project.Status.IN_PROGRESS);
        project2.setUser(getSampleUser());

        return project2;
    }

    public static List<Project> getProjects() {
        List<Project> projects = new ArrayList<>();
        Project project = new Project();
        project.setId(1L);
        project.setName("management");
        project.setDescription("big project");
        project.setStartDate(LocalDate.of(2025, 10, 5));
        project.setEndDate(LocalDate.of(2025, 11, 1));
        project.setStatus(Project.Status.INITIATED);
        project.setUser(getSampleUser());

        Project project2 = new Project();
        project2.setId(2L);
        project2.setName("marketing");
        project2.setDescription("small project");
        project2.setStartDate(LocalDate.of(2005, 10, 5));
        project2.setEndDate(LocalDate.of(2005, 11, 1));
        project2.setStatus(Project.Status.IN_PROGRESS);
        project2.setUser(getSampleUser());

        projects.add(project);
        projects.add(project2);

        return projects;
    }

    public static ProjectRequestDto getSampleProjectRequestDto() {
        return new ProjectRequestDto()
                .setName("management")
                .setDescription("big project")
                .setUserId(getSampleUser().getId())
                .setStartDate(LocalDate.of(2025, 10, 5))
                .setEndDate(LocalDate.of(2025, 11, 1));
    }

    public static ProjectResponseDto getSampleProjectResponseDto() {
        return new ProjectResponseDto()
                .setId(1L)
                .setName("management")
                .setDescription("big project")
                .setUserId(getSampleUser().getId())
                .setStatus(Project.Status.INITIATED)
                .setStartDate(LocalDate.of(2025, 10, 5))
                .setEndDate(LocalDate.of(2025, 11, 1));
    }

    public static ProjectResponseDto getSampleAnotherProjectResponseDto() {
        return new ProjectResponseDto()
                .setId(2L)
                .setName("marketing")
                .setDescription("small project")
                .setUserId(getSampleUser().getId())
                .setStatus(Project.Status.IN_PROGRESS)
                .setStartDate(LocalDate.of(2005, 10, 5))
                .setEndDate(LocalDate.of(2005, 11, 1));
    }
}
