package com.example.project.controller;

import com.example.project.entity.Student;
import com.example.project.service.student.StudentServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("admin")
public class ManagerStudentController {

    @Autowired
    private StudentServiceImp studentServiceImp;

    @GetMapping("managerStudent")
    public String managerStudent(Model model,
                                 @RequestParam(defaultValue = "1") int page,
                                 @RequestParam(defaultValue = "5") int size) {
        int totalStudent = studentServiceImp.totalStudents();
        int totalPage = (int) Math.ceil((double) totalStudent / size);

        model.addAttribute("content", "student");
        model.addAttribute("students", studentServiceImp.getAllStudents(page, size));
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPage", totalPage);
        return "managerStudent";
    }

    @GetMapping("/student")
    public String student(Model model,
                          @RequestParam(defaultValue = "1") int page,
                          @RequestParam(defaultValue = "5") int size,
                          @RequestParam(defaultValue = "") String sortBy,
                          @RequestParam(defaultValue = "asc") String sortType,
                          @RequestParam(defaultValue = "") String keyword,
                          @RequestParam(defaultValue = "false") boolean isConfirmLock,
                          @RequestParam(required = false) Integer id,
                          @RequestParam(defaultValue = "false") boolean isLock) {
        if(isLock) {
            studentServiceImp.lockStudent(id);
        }
        int totalStudent;
        if (!keyword.isEmpty()) {
            totalStudent = studentServiceImp.searchStudentCount(keyword);
        } else {
            totalStudent = studentServiceImp.totalStudents();
        }
        int totalPage = (int) Math.ceil((double) totalStudent / size);

        String sortField = "id";
        String sortDirection = "asc";
        List<Student> students = new ArrayList<>();
        if (!sortBy.isEmpty() && sortBy.contains("-")) {
            String[] parts = sortBy.split("-");
            if (parts.length == 2) {
                sortField = parts[0];
                sortDirection = parts[1];
            }
        }
        if (!keyword.isEmpty()) {
            students = studentServiceImp.searchStudent(keyword, page, size);
        } else if(!sortBy.isEmpty()) {
            students = studentServiceImp.sortStudent(sortField, sortDirection, page, size);
        }else {
            students = studentServiceImp.getAllStudents(page, size);
        }

        model.addAttribute("content", "student");
        model.addAttribute("students", students);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPage", totalPage);
        model.addAttribute("keyword", keyword);
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("sortType", sortType);
        model.addAttribute("isConfirmLock", isConfirmLock);
        if(isConfirmLock) {
            model.addAttribute("studentLock", studentServiceImp.findById(id));
            model.addAttribute("id", id);
        }
        return "dashboard";
    }
}
