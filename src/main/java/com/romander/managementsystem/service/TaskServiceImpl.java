package com.romander.managementsystem.service;

import com.romander.managementsystem.dto.project.UpdateTaskRequestDto;
import com.romander.managementsystem.dto.task.TaskRequestDto;
import com.romander.managementsystem.dto.task.TaskResponseDto;
import com.romander.managementsystem.exception.EntityNotFoundException;
import com.romander.managementsystem.mapper.TaskMapper;
import com.romander.managementsystem.model.Task;
import com.romander.managementsystem.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    @Override
    public TaskResponseDto createTask(TaskRequestDto taskRequestDto) {
        Task task = taskMapper.toModel(taskRequestDto);
        Task savedTask = taskRepository.save(task);
        return taskMapper.toDto(savedTask);
    }

    @Override
    public Page<TaskResponseDto> getTasks(Pageable pageable) {
        return taskRepository.findAll(pageable)
                .map(taskMapper::toDto);
    }

    @Override
    public TaskResponseDto getParticularTask(Long id) {
        Task task = findTaskById(id);
        return taskMapper.toDto(task);
    }

    @Override
    public TaskResponseDto updateTask(Long id, UpdateTaskRequestDto requestDto) {
        Task task = findTaskById(id);
        taskMapper.updateTask(task, requestDto);
        return taskMapper.toDto(task);
    }

    @Override
    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }

    private Task findTaskById(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Cannot find task with id: " + id));
    }
}
