package com.nishikant.task_service.service.impl;


import com.fasterxml.jackson.databind.util.BeanUtil;
import com.nishikant.task_service.dto.Task;
import com.nishikant.task_service.dto.TaskRequest;
import com.nishikant.task_service.dto.TaskResponse;
import com.nishikant.task_service.exception.TaskNotFoundException;
import com.nishikant.task_service.repository.TaskRepository;
import com.nishikant.task_service.service.TaskService;
import com.nishikant.task_service.strategy.TaskSortingStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final ApplicationContext applicationContext;

    @Async("taskExecutor")
    @Transactional(readOnly = true)
    @Override
    public CompletableFuture<List<TaskResponse>> getAlltasksAsync(String sortType) {
        List<Task> tasks = taskRepository.findAll();
        TaskSortingStrategy strategy = applicationContext.getBean(sortType, TaskSortingStrategy.class);
        List<Task> sorted = strategy.sort(tasks);
        List<TaskResponse> responses = sorted.stream().map(TaskResponse::fromEntity).collect(Collectors.toList());
        return CompletableFuture.completedFuture(responses);
    }

    @Transactional
    @Override
    public TaskResponse createTask(TaskRequest request) {
        Task task = new Task();
        BeanUtils.copyProperties(request, task);
        task = taskRepository.save(task);
        return TaskResponse.fromEntity(task);
    }

    @Transactional(readOnly = true)
    @Override
    public TaskResponse getTaskById(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("Task Not Found with Id: " + id));
        return TaskResponse.fromEntity(task);
    }
}
