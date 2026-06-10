package input;

import model.Task;

import java.util.List;

public interface GetTasksByProjectInput {
    List<Task>  getTasksByProject(Long  project_id);
}
