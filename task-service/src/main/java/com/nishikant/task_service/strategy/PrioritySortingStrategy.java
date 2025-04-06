package com.nishikant.task_service.strategy;

import com.nishikant.task_service.dto.Task;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component("priority")
public class PrioritySortingStrategy implements TaskSortingStrategy {
    private final List<String> priorityOrder =   List.of("LOW", "MEDIUM", "HIGH" );

    public List<Task> sort(List<Task> tasks) {
        return tasks.stream()
                .sorted(Comparator.comparing(task -> priorityOrder.indexOf(task.getPriority())))
                .collect(Collectors.toList());
    }
}
