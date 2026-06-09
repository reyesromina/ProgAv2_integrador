package model;

import exception.ValidationException;

import java.time.LocalDateTime;

public class Task {
    private final Long idTask;
    private final Project project;
    //Agregar variable title
    private final Integer estimateHours;
    private final TaskStatus status;
    private final LocalDateTime finishedAt;
    private final LocalDateTime createdAt;
    private final String title;

    private Task(Long idTask,
                 Project project,
                 Integer estimateHours,

                 TaskStatus status,
                 LocalDateTime finishedAt,
                 LocalDateTime createdAt, String title) {
        this.idTask = idTask;
        this.project = project;
        this.estimateHours = estimateHours;
        this.status = status;
        this.finishedAt = finishedAt;
        this.createdAt = createdAt;
        this.title = title;
    }

    public static Task newTask(
                               Project project,
                               Integer estimateHours,
                               TaskStatus status,
                               LocalDateTime finishedAt,
                               LocalDateTime createdAt,
                               String title) {



        if (estimateHours == null || estimateHours <= 0) {
            throw new ValidationException("The estimate hours can't be null or less than zero");
        }

        if (status == null) {
            throw new ValidationException("The task status can't be null.");
        }

        // finishedAt debe ser null excepto cuando la tarea está completada
        finishedAt = null;
        if (status == TaskStatus.DONE) {
            finishedAt = LocalDateTime.now();
        }

        return new Task(null, project, estimateHours, status, finishedAt, createdAt, title);
    }

    public Long getIdTask() {
        return idTask;
    }

    public Project getProject() {
        return project;
    }

    public Integer getEstimateHours() {
        return estimateHours;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public LocalDateTime getFinishedAt() {
        return finishedAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public String getTitle() {
        return title;
    }
}
