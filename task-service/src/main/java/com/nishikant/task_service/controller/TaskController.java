package com.nishikant.task_service.controller;


import com.nishikant.task_service.dto.Task;
import com.nishikant.task_service.dto.TaskRequest;
import com.nishikant.task_service.dto.TaskResponse;
import com.nishikant.task_service.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @GetMapping
    public CompletableFuture<ResponseEntity<List<TaskResponse>>> getAllTasks(@RequestParam(defaultValue = "deadline") String sort){
        return taskService.getAlltasksAsync(sort)
                .thenApply(ResponseEntity::ok);
    }

    @PostMapping
    public ResponseEntity<TaskResponse> createTask(@RequestBody TaskRequest taskRequest) {
        TaskResponse taskResponse = taskService.createTask(taskRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(taskResponse);
    }

    @GetMapping("/id")
    public ResponseEntity<TaskResponse> getTaskById(@PathVariable long id){
        TaskResponse taskResponse = taskService.getTaskById(id);
        return ResponseEntity.ok(taskResponse);
    }
}
