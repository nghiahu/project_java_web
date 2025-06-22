package com.example.project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
public class UserInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession(false);
        boolean isLoggedIn = session != null && session.getAttribute("userLogin") != null;
        if (!isLoggedIn) {
            response.sendRedirect(request.getContextPath() + "/login");
            return false;
        }
        return true;
    }
}
