package com.example.project.service.course;

import com.example.project.entity.Course;

import java.util.List;

public interface CourseService {
    List<Course> listCourse(int page, int size);
    Course getCourse(int id);
    void saveCourse(Course course);
    void deleteCourse(int id);
    int countCourse();
}
