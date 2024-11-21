package org.example.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;
import org.example.model.Task;

import java.time.LocalDateTime;
import java.util.List;

@Jacksonized
@Getter
@Builder
@Schema(name = "Dto эрика")
public class EpicOutDto {
    @Schema(name = "id эпика")
    private Long id;
    @Schema(name = "Имя Эпика")
    private String name;
    @Schema(name = "Id ответственного за эпик")
    private Long responsibleId;
    @Schema(name = "Список задач входящих в эпик")
    private List<TaskDto> tasks;
    @Schema(name = "Номер Ивента")
    private Long eventId;
    @Schema(name = "Дедлайн эпика", example = "2022-10-10 22:22:22")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime deadline;
}
