package com.nishikant.task_service.strategy;

import com.nishikant.task_service.dto.Task;

import java.util.List;

public interface TaskSortingStrategy {
    List<Task> sort(List<Task> tasks);
}
