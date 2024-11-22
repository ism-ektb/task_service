package org.example.mapper;

import org.example.dto.EpicInDto;
import org.example.dto.EpicOutDto;
import org.example.dto.EpicOutLiteDto;
import org.example.model.Epic;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = TaskListMapper.class)
public interface EpicMapper {
    Epic dtoToModel(EpicInDto epicInDto);
    EpicOutDto modelToDto(Epic epic);
    EpicOutLiteDto modelToLiteDto(Epic epic);
}
