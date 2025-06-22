package com.example.project.dto;

public class CourseStatistics {
    private int id;
    private String name;
    private int duration;
    private String image;
    private int totalStudents;

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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getTotalStudents() {
        return totalStudents;
    }

    public void setTotalStudents(int totalStudents) {
        this.totalStudents = totalStudents;
    }
}
