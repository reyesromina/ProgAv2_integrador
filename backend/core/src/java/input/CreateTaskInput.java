package input;

import model.Task;
import model.TaskStatus;

import java.time.LocalDateTime;

public interface CreateTaskInput {
    Task createTask(Long idProject, Integer estimateHours, TaskStatus status, LocalDateTime finishAt, LocalDateTime createAt, String title);
}
