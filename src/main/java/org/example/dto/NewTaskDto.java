package org.example.dto;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class NewTaskDto {

    private String title;
    private String description;
    private LocalDateTime deadline;
    private Long assigneeId;
    private Long authorId;
    private Long eventId;
}
