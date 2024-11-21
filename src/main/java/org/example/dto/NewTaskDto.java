package org.example.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

import java.time.LocalDateTime;
@Data
@Jacksonized
public class NewTaskDto {

    private String title;
    private String description;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime deadline;
    private Long assigneeId;
    private Long authorId;
    private Long eventId;
}
