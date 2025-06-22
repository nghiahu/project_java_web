package com.example.project.service.course;

import com.example.project.entity.Course;

import java.util.List;

public interface CourseService {
    List<Course> listCourse(int page, int size);
    Course getCourse(int id);
    void saveCourse(Course course);
    void deleteCourse(int id);
    int countCourse();
    boolean validateCourseName(String courseName,int courseId);
    List<Course> searchCourse(String courseName,int page, int size);
    List<Course> filCourse(String filter, String sortBy, String sortTYpe, int page, int size);
    int totalCourseSearch(String keyword);
    List<Course> totalCourse();
    void reStore(int id);
    List<Course> filterCourse(boolean status, int page, int size);
    int countFilterCourse(boolean status);
    List<Course> searchCourseClient(String courseName,int page, int size);
    int countSearchCourseClient(String keyword);
}
