package org.example.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

import java.time.LocalDateTime;

@Getter
@Builder
@Schema(description = "DTO для обновления группы")
@Jacksonized
public class EpicPatchDto {

    @Schema(name = "Название Эпика")
    private String name;
    @NotNull(message = "Поле ответственный не должно быть пустым")
    private Long responsibleId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(name = "Дедлайн Группы")
    @Future(message = "Дедлайн должен быть в будущем")
    private LocalDateTime deadline;
}
