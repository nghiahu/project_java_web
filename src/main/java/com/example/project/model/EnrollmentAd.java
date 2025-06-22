package com.example.project.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class EnrollmentAd {
    private int id;
    private String studentName;
    private String courseName;
    private LocalDate registrationDate;
    private StatusEnrollment statusEnrollment;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getRegistrationDate() {
        return registrationDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    public void setRegistrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
    }

    public StatusEnrollment getStatusEnrollment() {
        return statusEnrollment;
    }

    public void setStatusEnrollment(StatusEnrollment statusEnrollment) {
        this.statusEnrollment = statusEnrollment;
    }
}
