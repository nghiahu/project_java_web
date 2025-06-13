package com.example.project.controller;

import com.example.project.dto.StudentDTO;
import com.example.project.dto.UserLoginDTO;
import com.example.project.entity.Student;
import com.example.project.service.auth.AuthServiceImp;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class AutherController {

    @Autowired
    private AuthServiceImp authServiceImp;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("register")
    public String register(Model model) {
        model.addAttribute("student", new StudentDTO());
        return "register";
    }

    @GetMapping("login")
    public String login(Model model) {
        model.addAttribute("login", new UserLoginDTO());
        return "login";
    }

    @PostMapping("register-save")
    public String registerSave(@Valid @ModelAttribute("student") StudentDTO student, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "register";
        }
        boolean valiUserName = authServiceImp.validateUsername(student.getUsername());
        boolean valiEmail = authServiceImp.validateEmail(student.getEmail(), 0);
        boolean valiPhone = authServiceImp.validatePhoneNumber(student.getPhone(), 0);

        if(!valiUserName){
            model.addAttribute("errorName", "Tên đăng nhập đã tồn tại");
        }
        if(!valiEmail){
            model.addAttribute("errorEmail", "Email đã tồn tại");
        }
        if(!valiPhone){
            model.addAttribute("errorPhone", "Số điện thoại đã tồn tại");
        }
        if(!valiUserName || !valiEmail || !valiPhone) {
            return "register";
        }
        Student student1 = modelMapper.map(student, Student.class);
        authServiceImp.register(student1);
        return "redirect:/login";
    }

    @PostMapping("login-save")
    public String loginSave(@Valid @ModelAttribute("login")UserLoginDTO login, BindingResult bindingResult, Model model, HttpSession session) {
        if (bindingResult.hasErrors()) {
            return "login";
        }
        Student student = authServiceImp.login(login.getEmail(), login.getPassword());
        if (student == null) {
            model.addAttribute("error", "Tài khoản hoặc mật khẩu không đúng");
            return "login";
        }
        if(student.isRole()){
            session.setAttribute("userLogin", student);
            return "redirect:/dashboard";
        }
        session.setAttribute("userLogin", student);
        return "redirect:/";
    }

}
