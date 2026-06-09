package usecase;

import exception.BusinessRuleViolationException;
import exception.ResourceNotFoundException;
import input.CreateTaskInput;
import model.Project;
import model.Task;
import model.TaskStatus;
import output.ProjectRepository;
import output.TaskRepository;

import java.time.Clock;
import java.time.LocalDateTime;

public class CreateTaskUseCase implements CreateTaskInput {

    private final ProjectRepository projectRepository;
    private final TaskRepository taskRepository;
    private final Clock clock;

    public CreateTaskUseCase(Clock clock,ProjectRepository projectRepository, TaskRepository taskRepository) {
        this.projectRepository = projectRepository;
        this.taskRepository = taskRepository;
        this.clock = clock;
    }

    @Override
    public Task createTask(Long idProject,
                           Integer estimateHours,
                           TaskStatus status,
                           LocalDateTime finishAt,
                           LocalDateTime createAt,
                           String title) {
        Project project = projectRepository.findProjectById(idProject);
        if(project == null){
            throw new ResourceNotFoundException("Project not exist");
        }
        if(project.getProjectStatus()== model.ProjectStatus.CLOSED){
            throw new BusinessRuleViolationException("Project is already closed");
        }

        Task task= Task.newTask(project,estimateHours,status,finishAt,createAt, title);

        Task savedTask=taskRepository.saveTask(task);

        if(savedTask==null){
            throw new BusinessRuleViolationException("Task not saved");
        }
        return savedTask;
    }
}
