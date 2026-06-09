package com.undec.persistence.until;

import com.undec.persistence.entity.ProjectData;
import com.undec.persistence.entity.UserData;
import model.Project;
import model.ProjectStatus;

public class ProjectMapper {
    public static ProjectData mapToProjectData(Project project){

        ProjectData  taskData = new ProjectData( UserMapper.mapToUserData(project.getUser()),
              project.getName(),project.getProjectStatus(),project.getDescription() );

        if (project.getId() != null) {
            taskData.setId(project.getId());
        }
        return taskData;
    }

    public static Project mapToProjectDomain(ProjectData project){

        return Project.newProject(
                project.getName(),
                project.getProjectStatus(),
                project.getDescription(),
                UserMapper.mapToUserDomain(project.getUser()));

    }

}
