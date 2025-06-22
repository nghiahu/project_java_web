package com.example.project.service.student;

import com.example.project.entity.Student;
import com.example.project.repository.student.StudentRepositoryImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServiceImp implements StudentService {

    @Autowired
    private StudentRepositoryImp studentRepositoryImp;

    @Override
    public List<Student> findAll(int page, int size) {
        return studentRepositoryImp.findAll(page, size);
    }

    @Override
    public Student findById(int id) {
        return studentRepositoryImp.findById(id);
    }

    @Override
    public void lockStudent(int id) {
        studentRepositoryImp.lockStudent(id);
    }

    @Override
    public int totalStudents() {
        return studentRepositoryImp.totalStudents();
    }

    @Override
    public List<Student> getAllStudents(int page, int size) {
        return studentRepositoryImp.getAllStudents(page, size);
    }

    @Override
    public List<Student> sortStudent(String sortBy, String sortTYpe, int page, int size) {
        return studentRepositoryImp.sortStudent(sortBy, sortTYpe, page, size);
    }

    @Override
    public List<Student> searchStudent(String keyword, int page, int size) {
        return studentRepositoryImp.searchStudent(keyword, page, size);
    }

    @Override
    public int searchStudentCount(String keyword) {
        return studentRepositoryImp.searchStudentCount(keyword);
    }

    @Override
    public void updateStudent(Student student) {
        studentRepositoryImp.updateStudent(student);
    }
}
