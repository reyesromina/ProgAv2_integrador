package com.undec.persistence.entity;

import jakarta.persistence.*;
import model.TaskStatus;

import java.time.LocalDateTime;

@Entity
@Table(name = "tasks")
public class TaskData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTask;

    @ManyToOne
    @JoinColumn(name = "Projects")
    private ProjectData project;

    @Column(name = "Estimate_Hours")
    private Integer estimateHours;

    @Enumerated(EnumType.STRING)
    @Column(name = "Status")
    private TaskStatus status;
    @Column(name = "Finished_At")
    private LocalDateTime finishedAt;
    @Column(name = "Create_At")
    private LocalDateTime createdAt;
    @Column(name = "title")
    private String title;

    public TaskData() {
    }

    public TaskData(ProjectData project, Integer estimateHours, TaskStatus status, LocalDateTime finishedAt, LocalDateTime createdAt, String title) {

        this.project = project;
        this.estimateHours = estimateHours;

        this.status = status;
        this.finishedAt = finishedAt;
        this.createdAt = createdAt;
        this.title = title;
    }

    public Long getIdTask() {
        return idTask;
    }

    public void setIdTask(Long idTask) {
        this.idTask = idTask;
    }

    public ProjectData getProject() {
        return project;
    }

    public void setProject(ProjectData project) {
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
}
