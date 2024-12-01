package org.example.mapper;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.example.dto.TaskDto;
import org.example.model.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.12 (Amazon.com Inc.)"
)
@Component
public class TaskListMapperImpl implements TaskListMapper {

    @Autowired
    private TaskMapper taskMapper;

    @Override
    public List<TaskDto> modelsToDtos(List<Task> tasks) {
        if ( tasks == null ) {
            return null;
        }

        List<TaskDto> list = new ArrayList<TaskDto>( tasks.size() );
        for ( Task task : tasks ) {
            list.add( taskMapper.modelToDto( task ) );
        }

        return list;
    }
}
