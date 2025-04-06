package com.nishikant.task_service.strategy;


import com.nishikant.task_service.dto.Task;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component("deadline")
public class DeadlineSortingStrategy implements TaskSortingStrategy{

    public List<Task> sort(List<Task> tasks) {
        return tasks.stream()
                .sorted(Comparator.comparing(Task::getDeadLine))
                .collect(Collectors.toList());
    }
}
