package com.example.project.service.auth;

import com.example.project.entity.Student;
import com.example.project.repository.auth.AuthRepositoryImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImp implements AuthService {

    @Autowired
    private AuthRepositoryImp authRepositoryImp;

    @Override
    public void register(Student student) {
        authRepositoryImp.register(student);
    }

    @Override
    public Student login(String email, String password) {
        return authRepositoryImp.login(email, password);
    }

    @Override
    public boolean validateUsername(String username) {
        return authRepositoryImp.validateUsername(username);
    }

    @Override
    public boolean validatePhoneNumber(String phoneNumber, int id) {
        return authRepositoryImp.validatePhoneNumber(phoneNumber, id);
    }

    @Override
    public boolean validateEmail(String email, int id) {
        return authRepositoryImp.validateEmail(email, id);
    }
}
