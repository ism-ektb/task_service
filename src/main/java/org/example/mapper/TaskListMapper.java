package org.example.mapper;

import org.example.dto.TaskDto;
import org.example.model.Task;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = TaskMapper.class)
public interface TaskListMapper {
    List<TaskDto> modelsToDtos(List<Task> tasks);
}
