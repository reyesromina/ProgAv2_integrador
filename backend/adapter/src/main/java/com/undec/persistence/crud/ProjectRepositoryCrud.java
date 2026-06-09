package com.undec.persistence.crud;

import com.undec.persistence.entity.ProjectData;

import org.springframework.data.repository.CrudRepository;

public interface ProjectRepositoryCrud extends CrudRepository<ProjectData, Long> {
}
