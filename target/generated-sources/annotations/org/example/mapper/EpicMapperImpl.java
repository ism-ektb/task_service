package org.example.mapper;

import javax.annotation.processing.Generated;
import org.example.dto.EpicInDto;
import org.example.dto.EpicOutDto;
import org.example.dto.EpicOutLiteDto;
import org.example.model.Epic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.12 (Amazon.com Inc.)"
)
@Component
public class EpicMapperImpl implements EpicMapper {

    @Autowired
    private TaskListMapper taskListMapper;

    @Override
    public Epic dtoToModel(EpicInDto epicInDto) {
        if ( epicInDto == null ) {
            return null;
        }

        Epic.EpicBuilder<?, ?> epic = Epic.builder();

        epic.name( epicInDto.getName() );
        epic.responsibleId( epicInDto.getResponsibleId() );
        epic.eventId( epicInDto.getEventId() );
        epic.deadline( epicInDto.getDeadline() );

        return epic.build();
    }

    @Override
    public EpicOutDto modelToDto(Epic epic) {
        if ( epic == null ) {
            return null;
        }

        EpicOutDto.EpicOutDtoBuilder epicOutDto = EpicOutDto.builder();

        epicOutDto.id( epic.getId() );
        epicOutDto.name( epic.getName() );
        epicOutDto.responsibleId( epic.getResponsibleId() );
        epicOutDto.tasks( taskListMapper.modelsToDtos( epic.getTasks() ) );
        epicOutDto.eventId( epic.getEventId() );
        epicOutDto.deadline( epic.getDeadline() );

        return epicOutDto.build();
    }

    @Override
    public EpicOutLiteDto modelToLiteDto(Epic epic) {
        if ( epic == null ) {
            return null;
        }

        EpicOutLiteDto.EpicOutLiteDtoBuilder epicOutLiteDto = EpicOutLiteDto.builder();

        epicOutLiteDto.id( epic.getId() );
        epicOutLiteDto.name( epic.getName() );
        epicOutLiteDto.responsibleId( epic.getResponsibleId() );
        epicOutLiteDto.eventId( epic.getEventId() );
        epicOutLiteDto.deadline( epic.getDeadline() );

        return epicOutLiteDto.build();
    }
}
