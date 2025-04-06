package com.nishikant.task_service.service;


import com.nishikant.task_service.dto.TaskRequest;
import com.nishikant.task_service.dto.TaskResponse;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface TaskService {
    CompletableFuture<List<TaskResponse>> getAlltasksAsync(String sortType);
    TaskResponse createTask(TaskRequest request);
    TaskResponse getTaskById(Long id);
}
