package com.example.project.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CourseEnrollment {
    private int id;
    private String name;
    private int duration;
    private String instructor;
    private LocalDate create_at;
    private String image;
    private int enrollment_id;
    private StatusEnrollment status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getInstructor() {
        return instructor;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

    public String getCreate_at() {
        return create_at.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    public void setCreate_at(LocalDate create_at) {
        this.create_at = create_at;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getEnrollment_id() {
        return enrollment_id;
    }

    public void setEnrollment_id(int enrollment_id) {
        this.enrollment_id = enrollment_id;
    }

    public StatusEnrollment getStatus() {
        return status;
    }

    public void setStatus(StatusEnrollment status) {
        this.status = status;
    }
}
