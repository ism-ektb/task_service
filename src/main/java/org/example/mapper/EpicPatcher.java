package org.example.mapper;

import lombok.extern.slf4j.Slf4j;
import org.example.dto.EpicPatchDto;
import org.example.model.Epic;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class EpicPatcher {
    public Epic patch(Epic epic, EpicPatchDto patch){

        Epic newEpic = Epic.builder()
                .id(epic.getId())
                .name(patch.getName() != null ? patch.getName() : epic.getName())
                .responsibleId(patch.getResponsibleId() != null ? patch.getResponsibleId() : epic.getResponsibleId())
                .eventId(epic.getEventId())
                .deadline(patch.getDeadline() != null ? patch.getDeadline() : epic.getDeadline()).build();
        /*
        if (epicPatchDto != null) return epic;
        if (epicPatchDto.getName() != null) epic.setName(epicPatchDto.getName());
        if (epicPatchDto.getResponsibleId() != null) epic.setResponsibleId(epicPatchDto.getResponsibleId());
        if (epicPatchDto.getDeadline() != null) epic.setDeadline(epicPatchDto.getDeadline());

         */
    return newEpic;
    }
}
