package com.undec.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import model.Project;
import model.Task;
import model.TaskStatus;

import java.time.LocalDateTime;

public class TaskResponse {

    @JsonProperty("idTask")
    private Long idTask;
    @JsonProperty("project")
    private ProjectResponse project;
    //Agregar variable title
    @JsonProperty("estimateHours")
    private Integer estimateHours;

    @JsonProperty("status")
    private TaskStatus status;

    @JsonProperty("createdAt")
    private LocalDateTime createdAt;
    @JsonProperty("title")
    private String title;

    public TaskResponse(Long idTask,ProjectResponse project, Integer estimateHours, TaskStatus status,  LocalDateTime createdAt, String title) {
        this.idTask=idTask;
        this.project = project;
        this.estimateHours = estimateHours;

        this.status = status;

        this.createdAt = createdAt;
        this.title = title;
    }



    public ProjectResponse getProject() {
        return project;
    }

    public void setProject(ProjectResponse project) {
        this.project = project;
    }

    public Integer getEstimateHours() {
        return estimateHours;
    }

    public void setEstimateHours(Integer estimateHours) {
        this.estimateHours = estimateHours;
    }


    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }


    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getIdTask() {
        return idTask;
    }

    public void setIdTask(Long idTask) {
        this.idTask = idTask;
    }

    public static TaskResponse fromDomainTask(Task task){
        return new TaskResponse(task.getIdTask(), ProjectResponse.fromDomainProject(task.getProject()), task.getEstimateHours(),
                 task.getStatus(),task.getCreatedAt(), task.getTitle());
    }
}