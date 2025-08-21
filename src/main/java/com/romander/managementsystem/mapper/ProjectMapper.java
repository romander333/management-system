package com.romander.managementsystem.mapper;

import com.romander.managementsystem.config.MapperConfig;
import com.romander.managementsystem.dto.project.ProjectRequestDto;
import com.romander.managementsystem.dto.project.ProjectResponseDto;
import com.romander.managementsystem.model.Project;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfig.class)
public interface ProjectMapper {
    Project toModel(ProjectRequestDto requestDto);

    ProjectResponseDto toDto(Project project);

    @Mapping(target = "id", ignore = true)
    void updateModel(@MappingTarget Project project, ProjectRequestDto requestDto);
}
