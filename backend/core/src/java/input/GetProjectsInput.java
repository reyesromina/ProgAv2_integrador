package input;

import model.Project;

import java.util.List;

public interface GetProjectsInput {
    public List<Project> getProjects(Long user_id);
}
