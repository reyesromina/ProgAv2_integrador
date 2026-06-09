package com.undec.persistence.repository;

import com.undec.persistence.crud.ProjectRepositoryCrud;
import com.undec.persistence.crud.TaskRepositoryCrud;
import com.undec.persistence.entity.ProjectData;
import com.undec.persistence.entity.TaskData;
import com.undec.persistence.until.TaskMapper;
import model.Task;
import org.springframework.stereotype.Repository;
import output.TaskRepository;
@Repository
public class TaskRepositoryImpl implements TaskRepository {


    private final TaskRepositoryCrud taskRepositoryCrud;
    private final ProjectRepositoryCrud projectRepositoryCrud;

    public TaskRepositoryImpl(TaskRepositoryCrud taskRepositoryCrud, ProjectRepositoryCrud projectRepositoryCrud) {
        this.taskRepositoryCrud = taskRepositoryCrud;
        this.projectRepositoryCrud = projectRepositoryCrud;
    }
    @Override
    public Task saveTask(Task task) {
        System.out.println("Task Project ID: " +
                task.getProject().getId());
        TaskData data = TaskMapper.mapToTaskData(task);
        System.out.println("TaskData Project ID: " +
                data.getProject().getId());
        TaskData saved = taskRepositoryCrud.save(data);
        return TaskMapper.mapToTaskDomain(saved);

    }
}
