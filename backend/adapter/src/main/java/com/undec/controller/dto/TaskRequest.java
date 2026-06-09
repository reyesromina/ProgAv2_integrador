package com.undec.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import model.Project;
import model.Task;
import model.TaskStatus;

import java.time.LocalDateTime;

public class TaskRequest {



    @JsonProperty("estimateHours")
    private Integer estimateHours;

    @JsonProperty("status")
    private TaskStatus status;
    @JsonProperty("finishedAt")
    private LocalDateTime finishedAt;
    @JsonProperty("createdAt")
    private LocalDateTime createdAt;
    @JsonProperty("title")
    private String title;

    public TaskRequest(Integer estimateHours,  TaskStatus status, LocalDateTime finishedAt, LocalDateTime createdAt, String title) {


        this.estimateHours = estimateHours;

        this.status = status;
        this.finishedAt = finishedAt;
        this.createdAt = createdAt;
        this.title = title;
    }



    public Integer getEstimateHours() {return estimateHours;}

    public void setEstimateHours(Integer estimateHours) {
        this.estimateHours = estimateHours;
    }



    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public LocalDateTime getFinishedAt() {
        return finishedAt;
    }

    public void setFinishedAt(LocalDateTime finishedAt) {
        this.finishedAt = finishedAt;
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

   /* public Task toDomainTask(){
        return Task.newTask(this.project, this.estimateHours,
                this.status, this.finishedAt, this.createdAt, this.title);
    }
*/
}
