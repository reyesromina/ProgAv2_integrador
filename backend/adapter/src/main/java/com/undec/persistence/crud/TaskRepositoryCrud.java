package com.undec.persistence.crud;

import com.undec.persistence.entity.TaskData;
import model.Task;
import org.springframework.data.repository.CrudRepository;

public interface TaskRepositoryCrud extends CrudRepository<TaskData, Long> {
}
