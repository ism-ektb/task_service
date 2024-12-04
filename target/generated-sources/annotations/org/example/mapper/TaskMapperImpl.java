package org.example.mapper;

import javax.annotation.processing.Generated;
import org.example.dto.NewTaskDto;
import org.example.dto.TaskDto;
import org.example.model.Task;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.12 (Amazon.com Inc.)"
)
@Component
public class TaskMapperImpl implements TaskMapper {

    @Override
    public TaskDto modelToDto(Task task) {
        if ( task == null ) {
            return null;
        }

        TaskDto taskDto = new TaskDto();

        taskDto.setId( task.getId() );
        taskDto.setTitle( task.getTitle() );
        taskDto.setDescription( task.getDescription() );
        taskDto.setCreatedDateTime( task.getCreatedDateTime() );
        taskDto.setDeadline( task.getDeadline() );
        taskDto.setStatus( task.getStatus() );
        taskDto.setAssigneeId( task.getAssigneeId() );
        taskDto.setAuthorId( task.getAuthorId() );
        taskDto.setEventId( task.getEventId() );

        return taskDto;
    }

    @Override
    public Task dtoToModel(NewTaskDto newTaskDto) {
        if ( newTaskDto == null ) {
            return null;
        }

        Task.TaskBuilder task = Task.builder();

        task.title( newTaskDto.getTitle() );
        task.description( newTaskDto.getDescription() );
        task.deadline( newTaskDto.getDeadline() );
        task.assigneeId( newTaskDto.getAssigneeId() );
        task.authorId( newTaskDto.getAuthorId() );
        task.eventId( newTaskDto.getEventId() );

        return task.build();
    }
}
