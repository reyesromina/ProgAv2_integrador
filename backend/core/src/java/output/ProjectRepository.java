package output;

import model.Project;

import java.util.List;

public interface ProjectRepository {
    Project saveProject (Project project);
    Project findProjectById(Long idProject);
    List<Project> findAllProjects(Long user_id);
}
