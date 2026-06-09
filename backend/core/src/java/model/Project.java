package model;

import exception.ValidationException;

public class Project {
    private Long id;
    private String name;
    private ProjectStatus projectStatus;
    private String description;
    private User user;

    private Project(Long id, String name, ProjectStatus projectStatus, String description, User user) {
        this.id = id;
        this.name = name;

        this.projectStatus = projectStatus;
        this.description = description;
        this.user = user;
    }
    public static Project newProject(
                                     String name,
                                     ProjectStatus statusProject,
                                     String description,User user
                                     ) {

        if (name == null || name.trim().isEmpty()) {
            throw new ValidationException("The project name can't be null or empty.");
        }

        if (statusProject == null) {
            throw new ValidationException("The project status can't be null.");
        }
        if (description == null || description.trim().isEmpty()) {
            throw new ValidationException("The project description can't be null or empty.");
        }
        return new Project(null, name,statusProject, description,user);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public ProjectStatus getProjectStatus() {
        return projectStatus;
    }
    public String getDescription() {
        return description;
    }
    public User getUser() {
        return user;
    }
}
