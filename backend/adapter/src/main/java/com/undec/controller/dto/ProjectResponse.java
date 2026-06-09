package com.undec.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import model.Project;
import model.ProjectStatus;

public class ProjectResponse {
    @JsonProperty("idProject")
    private Long id;

    private String name;
    @JsonProperty("description")
    private String description;
    @JsonProperty("projectStatus")
    private ProjectStatus projectStatus;
    @JsonProperty("user")
    private UserResponse user;

    public ProjectResponse() {
    }

    public ProjectResponse(Long id, String name, String description, ProjectStatus projectStatus, UserResponse user) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.projectStatus = projectStatus;
        this.user = user;
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

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ProjectStatus getProjectStatus() {
        return projectStatus;
    }

    public void setProjectStatus(ProjectStatus projectStatus) {
        this.projectStatus = projectStatus;
    }

    public UserResponse getUser() {
        return user;
    }

    public void setUser(UserResponse user) {
        this.user = user;
    }

    public static ProjectResponse fromDomainProject(Project project) {
        if (project == null) return null;

        return new ProjectResponse(
                project.getId(),
                project.getName(),
                project.getDescription(),
                project.getProjectStatus(),
                UserResponse.fromDomainUser(project.getUser())
                );
    }
}