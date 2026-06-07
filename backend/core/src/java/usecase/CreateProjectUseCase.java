package usecase;

import exception.ResourceNotFoundException;
import exception.ValidationException;
import input.CreateProjectInput;
import model.Project;
import model.ProjectStatus;
import model.User;
import output.ProjectRepository;
import output.TokenService;
import output.UserRepository;

public class CreateProjectUseCase implements CreateProjectInput {
    private final TokenService tokenService;
    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;

    public CreateProjectUseCase(TokenService tokenService, UserRepository userRepository, ProjectRepository projectRepository) {
    this.tokenService = tokenService;
    this.userRepository = userRepository;
    this.projectRepository = projectRepository;
    }


    @Override
    public Project createProject(String emailToken, String name, String description, ProjectStatus projectStatus) {

        String email=tokenService.extractEmail(emailToken);
        User user=userRepository.findByEmail(email);
        if(user==null){
            throw new ResourceNotFoundException("User not found");
        }

        Project project = Project.newProject(name,projectStatus,description,user);

      Project savedProject= projectRepository.saveProject(project);
        if (savedProject == null) {
            throw new ValidationException("User could not be saved");
        }
        return savedProject;
    }
}
