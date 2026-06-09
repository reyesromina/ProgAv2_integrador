package usecase;

import model.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import output.ProjectRepository;
import output.TaskRepository;

import java.time.Clock;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CreateTaskTest {

    @Mock
    ProjectRepository projectRepository;
    @Mock
    TaskRepository taskRepository;


    @Test
    public void createTaskTest(){
        Clock clock = Clock.systemDefaultZone();
        CreateTaskUseCase useCase= new CreateTaskUseCase(clock,projectRepository,taskRepository);

        User user= User.createUserFactory(
                "emailExample@gmail.com",
                "1234", LocalDateTime.now(Clock.systemDefaultZone()),
                "3333");

        Project project= Project.newProject(
                "Project1",
                ProjectStatus.ACTIVE,
                "prueba",
                user
               );
        when(projectRepository.findProjectById(91L)).thenReturn(project);

        when(taskRepository.saveTask(any(Task.class))).thenReturn(Task.newTask(project,122,
                 TaskStatus.IN_PROGRESS, LocalDateTime.now(clock),LocalDateTime.now(clock).plusDays(3), "title"));

        Task task= useCase.createTask(91L,122,
                 TaskStatus.IN_PROGRESS, LocalDateTime.now(clock),LocalDateTime.now(clock), "title");

        Assertions.assertNotNull(task);
        Assertions.assertEquals("title", task.getTitle());
        Assertions.assertEquals(122, task.getEstimateHours());
        Assertions.assertEquals(TaskStatus.IN_PROGRESS, task.getStatus());
        Assertions.assertEquals(project, task.getProject());
        Assertions.assertSame(project, task.getProject());
    }
}
