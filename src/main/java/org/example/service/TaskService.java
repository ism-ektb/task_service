package org.example.service;

import org.example.dto.NewTaskDto;
import org.example.dto.TaskDto;
import org.example.dto.UpdateTaskDto;

import java.util.List;

public interface TaskService {
    TaskDto createTask(NewTaskDto newTask);

    TaskDto updateTask(Long userId, Long id, UpdateTaskDto updateTask);

    TaskDto getTask(long id);

    List<TaskDto> getTasks(int page, int size, Long eventId, Long assigneeId, Long authorId);

    void delete(Long userId, long id);
}
