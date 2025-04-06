package com.nishikant.task_service.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class TaskRequest {
    private String title;
    private String description;
    private LocalDate deadline;
    private String priority;
    private String status;
}
