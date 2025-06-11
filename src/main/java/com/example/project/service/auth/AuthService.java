package com.example.project.service.auth;

import com.example.project.entity.Student;

public interface AuthService {
    void register(Student student);
    Student login (String email, String password);
    boolean validateUsername(String username);
    boolean validatePhoneNumber(String phoneNumber, int id);
    boolean validateEmail(String email, int id);
}
