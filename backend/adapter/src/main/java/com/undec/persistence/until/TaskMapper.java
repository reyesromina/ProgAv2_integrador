package com.undec.persistence.until;

import com.undec.persistence.entity.TaskData;
import model.Task;

public class TaskMapper {
    public static TaskData mapToTaskData(Task task){


        TaskData  taskData = new TaskData( ProjectMapper.mapToProjectData(task.getProject()),
                task.getEstimateHours(),

                task.getStatus(),
                task.getFinishedAt(),
                task.getCreatedAt(),
                task.getTitle());

        if (task.getIdTask() != null) {
            taskData.setIdTask(task.getIdTask());
        }

        return taskData;

    }

    public static Task mapToTaskDomain(TaskData task){

        return Task.newTask(
                ProjectMapper.mapToProjectDomain(task.getProject()),
                task.getEstimateHours(),
                task.getStatus(),
                task.getFinishedAt(),
                task.getCreatedAt(), task.getTitle());

    }

}
