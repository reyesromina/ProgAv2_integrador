package output;

import model.Task;

import java.util.List;

public interface TaskRepository {
    Task saveTask(Task task);
    List<Task> findTasksByProject(Long projectId);
}
