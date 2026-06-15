package com.undec.persistence.crud;

import com.undec.persistence.entity.ProjectData;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProjectRepositoryCrud extends CrudRepository<ProjectData, Long> {
    List<ProjectData> findByUser_Id(long projectId);
}
