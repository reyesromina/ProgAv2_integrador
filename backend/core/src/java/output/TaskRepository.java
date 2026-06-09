package output;

import model.Task;

public interface TaskRepository {
    Task saveTask(Task task);
}
