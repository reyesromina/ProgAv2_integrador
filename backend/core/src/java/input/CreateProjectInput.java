package input;

import model.Project;
import model.ProjectStatus;

public interface CreateProjectInput {
    Project createProject(String emailToken, String name, String description, ProjectStatus projectStatus);
}
