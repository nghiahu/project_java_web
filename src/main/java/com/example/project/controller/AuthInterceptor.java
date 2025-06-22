package com.example.project.controller;

import com.example.project.entity.Student;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userLogin") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return false;
        }

        Student student = (Student) session.getAttribute("userLogin");
        if (!student.isRole()) {
            response.sendRedirect(request.getContextPath() + "/login");
            return false;
        }

        return true;
    }

}
