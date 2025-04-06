package com.nishikant.task_service.dto;


import lombok.Data;

import java.time.LocalDate;

@Data
public class TaskResponse {

    private Long Id;
    private String title;
    private String description;
    private LocalDate deadLine;
    private String priority;
    private String status;


    public static TaskResponse fromEntity(Task task) {
        TaskResponse res = new TaskResponse();
        res.setId(task.getId());
        res.setTitle(task.getTitle());
        res.setDescription(task.getDescription());
        res.setDeadLine(task.getDeadLine());
        res.setPriority(task.getPriority());
        res.setStatus(task.getStatus());
        return res;
    }
}
