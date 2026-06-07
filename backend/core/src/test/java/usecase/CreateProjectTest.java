package usecase;

import model.Project;
import model.ProjectStatus;
import model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import output.PasswordEncoderPort;
import output.ProjectRepository;
import output.TokenService;
import output.UserRepository;

import java.time.Clock;
import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)
public class CreateProjectTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private ProjectRepository projectRepository;
    @Mock
    private TokenService tokenService;


    @Test
    public void CreateProjectTest()
    {
        User user= User.createUserFactory(
                "emailExample@gmail.com",
                "1234", LocalDateTime.now(Clock.systemDefaultZone()),
                "3333");
        CreateProjectUseCase useCase= new CreateProjectUseCase(tokenService,userRepository,projectRepository);
        when(tokenService.extractEmail("emailToken")).thenReturn("emailExample@gmail.com");
        when(userRepository.findByEmail("emailExample@gmail.com")).thenReturn(user);

        Project project = Project.newProject("fisica", ProjectStatus.PLANNED,"proyecto de fisica",user);


        when(projectRepository.saveProject(any(Project.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Project project1= useCase.createProject("emailToken","fisica","proyecto de fisica",ProjectStatus.ACTIVE);

        Assertions.assertEquals(project1.getUser().getEmailUser(),project.getUser().getEmailUser());
        Assertions.assertEquals(
                "fisica",
                project1.getName()
        );
        Assertions.assertEquals(
                "proyecto de fisica",
                project1.getDescription()
        );
        Assertions.assertEquals(
                ProjectStatus.ACTIVE,
                project1.getProjectStatus()
        );
        Assertions.assertEquals(
                "emailexample@gmail.com",
                project1.getUser().getEmailUser().getEmail()
        );

    }
}
