package com.example.project.controller;

import com.example.project.dto.UserLoginDTO;
import com.example.project.entity.Student;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@Controller
public class DashboardController {

//    @GetMapping("dashboard")
//    public String dashboard(Model model, HttpSession session) {
//        Student student = (Student) session.getAttribute("userLogin");
//        if (student == null) {
//            model.addAttribute("login", new UserLoginDTO());
//            return "login";
//        }
//        return "dashboard";
//    }
}
