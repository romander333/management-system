package com.romander.managementsystem.service;

import com.romander.managementsystem.dto.project.UpdateTaskRequestDto;
import com.romander.managementsystem.dto.task.TaskRequestDto;
import com.romander.managementsystem.dto.task.TaskResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TaskService {
    TaskResponseDto createTask(TaskRequestDto taskRequestDto);

    Page<TaskResponseDto> getTasks(Pageable pageable);

    TaskResponseDto getParticularTask(Long id);

    TaskResponseDto updateTask(Long id, UpdateTaskRequestDto requestDto);

    void deleteTask(Long id);
}

