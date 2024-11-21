package org.example.mapper;

import org.example.dto.NewTaskDto;
import org.example.dto.TaskDto;
import org.example.model.Task;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TaskMapper {
    TaskDto modelToDto(Task task);
    Task dtoToModel(NewTaskDto newTaskDto);
}
