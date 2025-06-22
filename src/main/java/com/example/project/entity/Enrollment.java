package com.example.project.entity;

import com.example.project.model.StatusEnrollment;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Enrollment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;
    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;
    @Column(insertable = false, updatable = false, columnDefinition = "date default(now())")
    private LocalDateTime registered_at;
    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "enum('CANCEL','CONFIRMED','DENIED','WAITING')")
    private StatusEnrollment status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public LocalDateTime getRegistered_at() {
        return registered_at;
    }

    public void setRegistered_at(LocalDateTime registered_at) {
        this.registered_at = registered_at;
    }

    public StatusEnrollment getStatus() {
        return status;
    }

    public void setStatus(StatusEnrollment status) {
        this.status = status;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }
}
