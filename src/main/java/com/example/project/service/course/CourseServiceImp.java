package com.example.project.service.course;

import com.example.project.entity.Course;
import com.example.project.repository.course.CourseRepositoryImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseServiceImp implements CourseService {

    @Autowired
    private CourseRepositoryImp courseRepositoryImp;

    @Override
    public List<Course> listCourse(int page, int size) {
        return courseRepositoryImp.listCourse(page, size);
    }

    @Override
    public Course getCourse(int id) {
        return courseRepositoryImp.getCourse(id);
    }

    @Override
    public void saveCourse(Course course) {
        courseRepositoryImp.saveCourse(course);
    }

    @Override
    public void deleteCourse(int id) {
        courseRepositoryImp.deleteCourse(id);
    }

    @Override
    public int countCourse() {
        return courseRepositoryImp.countCourse();
    }

    @Override
    public boolean validateCourseName(String courseName, int courseId) {
        return courseRepositoryImp.validateCourseName(courseName, courseId);
    }

    @Override
    public List<Course> searchCourse(String courseName, int page, int size) {
        return courseRepositoryImp.searchCourse(courseName, page, size);
    }

    @Override
    public List<Course> filCourse(String filter, String sortBy, String sortTYpe, int page, int size) {
        return courseRepositoryImp.filCourse(filter, sortBy, sortTYpe, page, size);
    }

    @Override
    public int totalCourseSearch(String keyword) {
        return courseRepositoryImp.totalCourseSearch(keyword);
    }

    @Override
    public List<Course> totalCourse() {
        return courseRepositoryImp.totalCourse();
    }

    @Override
    public void reStore(int id) {
        courseRepositoryImp.reStore(id);
    }

    @Override
    public List<Course> filterCourse(boolean status, int page, int size) {
        return courseRepositoryImp.filterCourse(status, page, size);
    }

    @Override
    public int countFilterCourse(boolean status) {
        return courseRepositoryImp.countFilterCourse(status);
    }

    @Override
    public List<Course> searchCourseClient(String courseName, int page, int size) {
        return courseRepositoryImp.searchCourseClient(courseName, page, size);
    }

    @Override
    public int countSearchCourseClient(String keyword) {
        return courseRepositoryImp.countSearchCourseClient(keyword);
    }
}
