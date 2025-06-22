package com.example.project.service.student;

import com.example.project.entity.Student;

import java.util.List;

public interface StudentService {
    List<Student> findAll(int page, int size);
    Student findById(int id);
    void lockStudent(int id);
    int totalStudents();
    List<Student> getAllStudents(int page, int size);
    List<Student> sortStudent(String sortBy, String sortTYpe, int page, int size);
    List<Student> searchStudent(String keyword, int page, int size);
    int searchStudentCount(String keyword);
    void updateStudent(Student student);
}
