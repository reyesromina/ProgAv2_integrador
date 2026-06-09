package com.undec.persistence.entity;

import jakarta.persistence.*;
import model.ProjectStatus;

@Entity
@Table(name = "projects")
public class ProjectData {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @ManyToOne
        @JoinColumn(name = "USER")
        private UserData user;

        @Column(name="name",nullable = false)

        private String name;
        //enum -> almacenar como STRING para legibilidad y compatibilidad
        @Enumerated(EnumType.STRING)  //guarda el nombre del enum ("PLANNED", "ACTIVE", "CLOSED")
        @Column(name = "project_status", nullable = false)
        private ProjectStatus projectStatus;

        @Column(name="description",nullable = false)
        private String description;

        public ProjectData() {
        }
        public ProjectData(UserData user,
                           String name,
                           ProjectStatus projectStatus,
                           String description) {

            this.name = name;

            this.projectStatus = projectStatus;
            this.description = description;
            this.user = user;
        }

        public Long getId() {return id;}
        public String getName() {return name;}
        public ProjectStatus getProjectStatus() {return projectStatus;}
        public String getDescription() {return description;}
        public void setId(Long id) {this.id = id;}

         public UserData getUser() {
        return user;
         }
        public void setUser(UserData user) {
        this.user = user;
    }
}

