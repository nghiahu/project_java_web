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
}
