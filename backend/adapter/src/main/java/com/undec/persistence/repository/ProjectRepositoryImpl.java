package com.undec.persistence.repository;

import com.undec.persistence.crud.ProjectRepositoryCrud;
import com.undec.persistence.entity.ProjectData;
import com.undec.persistence.entity.UserData;
import com.undec.persistence.until.ProjectMapper;
import com.undec.persistence.until.UserMapper;
import model.Project;
import org.springframework.stereotype.Repository;
import output.ProjectRepository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class ProjectRepositoryImpl implements ProjectRepository {

    private final ProjectRepositoryCrud projectRepositoryCrud;

    public ProjectRepositoryImpl(ProjectRepositoryCrud projectRepositoryCrud) {
        this.projectRepositoryCrud = projectRepositoryCrud;
    }

    @Override
    public Project saveProject(Project project) {

        ProjectData projectData = ProjectMapper.mapToProjectData(project);

        projectRepositoryCrud.save(projectData);

        return ProjectMapper.mapToProjectDomain(projectData);

    }

    @Override
    public Project findProjectById(Long idProject) {
        return projectRepositoryCrud.findById(idProject)
                .map(ProjectMapper::mapToProjectDomain)
                .orElse(null);
    }

    @Override
    public List<Project> findAllProjects(Long user_id) {
        return projectRepositoryCrud.findByUser_Id(user_id).
                stream().
                map(ProjectMapper::mapToProjectDomain).
                collect(Collectors.toList());
    }

}
