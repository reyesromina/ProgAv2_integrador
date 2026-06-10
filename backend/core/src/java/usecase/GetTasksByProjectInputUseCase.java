package usecase;

import exception.ResourceNotFoundException;
import input.GetTasksByProjectInput;
import model.Project;
import model.Task;
import output.ProjectRepository;
import output.TaskRepository;

import java.util.List;

public class GetTasksByProjectInputUseCase implements GetTasksByProjectInput {

    private final ProjectRepository projectRepository;
    private final TaskRepository taskRepository;

    public GetTasksByProjectInputUseCase(ProjectRepository projectRepository, TaskRepository taskRepository) {
        this.projectRepository = projectRepository;
        this.taskRepository = taskRepository;
    }

    @Override
    public List<Task> getTasksByProject(Long project_id) {
        Project project=projectRepository.findProjectById(project_id);
        if(project==null){
            throw new ResourceNotFoundException("Project not found");
        }
        List<Task> tasks=taskRepository.findTasksByProject(project_id);
        if(tasks==null){
            throw new ResourceNotFoundException("Tasks not found");
        }

        return tasks;
    }
}
