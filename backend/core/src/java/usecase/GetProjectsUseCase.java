package usecase;


import exception.ResourceNotFoundException;
import input.GetProjectsInput;
import model.Project;
import output.ProjectRepository;
import output.UserRepository;


import java.util.List;


public class GetProjectsUseCase implements GetProjectsInput {


    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;


    public GetProjectsUseCase(UserRepository userRepository, ProjectRepository projectRepository) {
        this.userRepository = userRepository;
        this.projectRepository = projectRepository;
    }


    @Override
    public List<Project> getProjects(Long user_id) {


        if(userRepository.findUserById(user_id)==null){
            throw new ResourceNotFoundException("User not found");
        }
        List<Project> projects=projectRepository.findAllProjects(user_id);




        return projects;
    }
}
