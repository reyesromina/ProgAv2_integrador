package com.undec.persistence.crud;

import com.undec.persistence.entity.TaskData;
import model.Task;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TaskRepositoryCrud extends CrudRepository<TaskData, Long> {
    List<TaskData> findByProject_Id(long projectId);
}
