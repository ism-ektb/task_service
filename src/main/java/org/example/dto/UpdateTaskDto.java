package org.example.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UpdateTaskDto {

    private String title;
    private String description;
    private LocalDateTime deadline;
    private String status;
    private Long assigneeId;
    private Long eventId;
}
