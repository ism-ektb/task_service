package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.dto.NewTaskDto;
import org.example.dto.TaskDto;
import org.example.dto.UpdateTaskDto;
import org.example.exeptions.ConflictException;
import org.example.exeptions.StorageException;
import org.example.mapper.TaskListMapper;
import org.example.mapper.TaskMapper;
import org.example.model.Task;
import org.example.repository.TaskRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

public interface TaskService {
    TaskDto createTask(NewTaskDto newTask);

    TaskDto updateTask(Long userId, Long id, UpdateTaskDto updateTask);

    TaskDto getTask(long id);

    List<TaskDto> getTasks(int page, int size, Long eventId, Long assigneeId, Long authorId);

    void delete(Long userId, long id);
}