package com.romander.managementsystem.mapper;

import com.romander.managementsystem.config.MapperConfig;
import com.romander.managementsystem.dto.project.UpdateTaskRequestDto;
import com.romander.managementsystem.dto.task.TaskRequestDto;
import com.romander.managementsystem.dto.task.TaskResponseDto;
import com.romander.managementsystem.model.Task;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfig.class)
public interface TaskMapper {

    Task toModel(TaskRequestDto requestDto);

    @Mapping(target = "projectId", source = "project.id")
    @Mapping(target = "userId", source = "user.id")
    TaskResponseDto toDto(Task task);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "project", ignore = true)
    @Mapping(target = "user", ignore = true)
    void updateTask(@MappingTarget Task task, UpdateTaskRequestDto requestDto);
}
