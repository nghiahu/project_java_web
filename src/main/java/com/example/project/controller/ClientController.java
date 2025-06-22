package com.example.project.controller;

import com.example.project.dto.PasswordDTO;
import com.example.project.dto.StudentDTO;
import com.example.project.entity.Course;
import com.example.project.entity.Enrollment;
import com.example.project.entity.Student;
import com.example.project.model.CourseClient;
import com.example.project.model.CourseEnrollment;
import com.example.project.model.StatusEnrollment;
import com.example.project.service.auth.AuthServiceImp;
import com.example.project.service.course.CourseServiceImp;
import com.example.project.service.enrollment.EnrollmentServiceImp;
import com.example.project.service.student.StudentServiceImp;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("client")
public class ClientController {

    @Autowired
    private CourseServiceImp courseServiceImp;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private EnrollmentServiceImp enrollmentServiceImp;

    @Autowired
    private StudentServiceImp studentServiceImp;
    @GetMapping("client")
    public String client(Model model) {
        return "client";
    }

    @Autowired
    private AuthServiceImp authServiceImp;

    @GetMapping("/listCourse")
    public String listCourse(Model model, HttpSession session,
                             @RequestParam(defaultValue = "1") int page,
                             @RequestParam(defaultValue = "5") int size,
                             @RequestParam(defaultValue = "false") boolean isFormConfirm,
                             @RequestParam(required = false) Integer id,
                             @RequestParam(defaultValue = "") String keyword) {
        Student student = (Student) session.getAttribute("userLogin");
        if(student.isRole()){
            model.addAttribute("isRegis", false);
        }
        model.addAttribute("isRegis", true);
        int totalCourse;
        if (!keyword.isEmpty()) {
            totalCourse = courseServiceImp.countSearchCourseClient(keyword);
        } else {
            totalCourse = courseServiceImp.countCourse();
        }
        int totalPage = (int) Math.ceil((double) totalCourse / size);
        List<Course> courses;
        if (!keyword.isEmpty()) {
            courses = courseServiceImp.searchCourseClient(keyword, page, size);
        }else {
            courses = courseServiceImp.listCourse(page, size);
        }
        List<CourseClient> courseClients = new ArrayList<>();
        for (Course course : courses) {
            CourseClient courseClient = modelMapper.map(course, CourseClient.class);
            courseClient.setStatus(enrollmentServiceImp.isRegistered(course.getId(), student.getId()));
            courseClients.add(courseClient);
        }
        if (isFormConfirm) {
            Course course = courseServiceImp.getCourse(id);
            model.addAttribute("course", course);
        }
        model.addAttribute("content", "listCourse");
        model.addAttribute("totalPage", totalPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("courses", courseClients);
        model.addAttribute("keyword", keyword);
        model.addAttribute("isFormConfirm", isFormConfirm);
        return "client";
    }
    @GetMapping("/confirmRegister")
    public String confirmRegister(Model model,
                                  @RequestParam(defaultValue = "") String keyword,
                                  @RequestParam("id") int id) {
        model.addAttribute("keyword", keyword);
        model.addAttribute("course", courseServiceImp.getCourse(id));
        return "confirmRegister";
    }
    @PostMapping("registerEnrollment")
    public String registerEnrollment(@RequestParam(required = false) Integer courseId, HttpSession session) {
        Enrollment enrollment = new Enrollment();
        enrollment.setCourse(courseServiceImp.getCourse(courseId));
        Student student = (Student) session.getAttribute("userLogin");
        enrollment.setStudent(student);
        enrollment.setStatus(StatusEnrollment.WAITING);
        enrollmentServiceImp.saveEnrollment(enrollment);
        return "redirect:/client/listCourse";
    }

    @GetMapping("/historyEnrollment")
    public String historyEnrollment(Model model, HttpSession session,
                                    @RequestParam(defaultValue = "1") int page,
                                    @RequestParam(defaultValue = "5") int size,
                                    @RequestParam(required = false) String keyword,
                                    @RequestParam(required = false) String sort,
                                    @RequestParam(required = false) boolean isFormCancel,
                                    @RequestParam(defaultValue = "false") boolean cancel,
                                    @RequestParam(required = false) Integer id) {
        Student student = (Student) session.getAttribute("userLogin");

        List<Enrollment> enrollments;
        int totalEnrollment;

        if(cancel){
            enrollmentServiceImp.cancelEnrollment(id);
        }

        if (keyword != null && !keyword.isEmpty()) {
            enrollments = enrollmentServiceImp.searchEnrollmentByCourse(student.getId(), keyword, page, size);
            totalEnrollment = enrollmentServiceImp.countEnrollmentByCourse(student.getId(), keyword);
        } else if (sort != null && !sort.isEmpty()) {
            enrollments = enrollmentServiceImp.sortEnrollment(student.getId(), sort, page, size);
            totalEnrollment = enrollmentServiceImp.countEnrollmentsByStd(student.getId());
        } else {
            enrollments = enrollmentServiceImp.getEnrollmentsByStd(student.getId(), page, size);
            totalEnrollment = enrollmentServiceImp.countEnrollmentsByStd(student.getId());
        }

        List<CourseEnrollment> courseEnrollments = new ArrayList<>();
        for (Enrollment enrollment : enrollments) {
            Course course = courseServiceImp.getCourse(enrollment.getCourse().getId());
            CourseEnrollment courseEnrollment = modelMapper.map(course, CourseEnrollment.class);
            courseEnrollment.setStatus(enrollment.getStatus());
            courseEnrollment.setEnrollment_id(enrollment.getId());
            courseEnrollments.add(courseEnrollment);
        }

        int totalPage = (int) Math.ceil((double) totalEnrollment / size);

        model.addAttribute("currentPage", page);
        model.addAttribute("totalPage", totalPage);
        model.addAttribute("keyword", keyword);
        model.addAttribute("sort", sort);
        model.addAttribute("courseEnrollments", courseEnrollments);
        model.addAttribute("content", "listEnrollment");
        model.addAttribute("isFormConfirm", isFormCancel);
        model.addAttribute("id", id);
        return "client";
    }

    @GetMapping("/enrollmentClient")
    public String EnrollmentClient(Model model,
                                   @RequestParam(defaultValue = "1") int page,
                                   @RequestParam(defaultValue = "5") int size){
        Student student = (Student) model.getAttribute("userLogin");
        List<Enrollment> enrollments = enrollmentServiceImp.getEnrollmentsByStd(student.getId(), page, size);
        List<CourseEnrollment> courseEnrollments = new ArrayList<>();
        for (Enrollment enrollment : enrollments) {
            Course course = courseServiceImp.getCourse(enrollment.getCourse().getId());
            CourseEnrollment courseEnrollment = modelMapper.map(course, CourseEnrollment.class);
            courseEnrollment.setStatus(enrollment.getStatus());
            courseEnrollment.setEnrollment_id(enrollment.getId());
            courseEnrollments.add(courseEnrollment);
        }
        model.addAttribute("courseEnrollments", courseEnrollments);
        return "historyEnrollment";
    }

    @GetMapping("myProfile")
    public String myProfile(Model model, HttpSession session,
                            @RequestParam(defaultValue = "false") boolean isChangePassword){
        Student student = (Student) session.getAttribute("userLogin");
        StudentDTO studentDTO = modelMapper.map(studentServiceImp.findById(student.getId()), StudentDTO.class);
        model.addAttribute("student", studentDTO);
        model.addAttribute("content", "profile");
        model.addAttribute("isChangePassword", isChangePassword);
        if(isChangePassword){
            model.addAttribute("passwordDTO", new PasswordDTO());
        }
        return "client";
    }
    @GetMapping("profile")
    public String profile(Model model, HttpSession session){
        Student student = (Student) session.getAttribute("userLogin");
        StudentDTO studentDTO = modelMapper.map(studentServiceImp.findById(student.getId()), StudentDTO.class);
        model.addAttribute("student", studentDTO);
        return "profile";
    }

    @PostMapping("changeProfile")
    public String changeProfile(@Valid @ModelAttribute("student") StudentDTO studentDTO, BindingResult bindingResult, Model model, HttpSession session){
        if(bindingResult.hasErrors()){
            model.addAttribute("content", "profile");
            return "client";
        }
        boolean valiEmail = authServiceImp.validateEmail(studentDTO.getEmail(), studentDTO.getId());
        boolean valiPhone = authServiceImp.validatePhoneNumber(studentDTO.getPhone(), studentDTO.getId());

        if(!valiEmail){
            model.addAttribute("errorEmail", "Email đã tồn tại");
        }
        if(!valiPhone){
            model.addAttribute("errorPhone", "Số điện thoại đã tồn tại");
        }
        if(!valiEmail || !valiPhone) {
            model.addAttribute("content", "profile");
            return "client";
        }
        Student student = modelMapper.map(studentDTO, Student.class);
        studentServiceImp.updateStudent(student);
        return "redirect:/client/myProfile";
    }

    @PostMapping("changePassword")
    public String changePassword(@Valid @ModelAttribute("passwordDTO") PasswordDTO passwordDTO,
                                 BindingResult bindingResult, Model model, HttpSession session,
                                 @RequestParam(defaultValue = "true") boolean isChangePassword){
        Student student = (Student) session.getAttribute("userLogin");
        Student oldStudent = studentServiceImp.findById(student.getId());
        boolean isCorrectPassword = oldStudent.getPassword().equals(passwordDTO.getPassword());
        boolean isConfirmPassword = passwordDTO.getNewPassword().equals(passwordDTO.getConfirmPassword());
        if(bindingResult.hasErrors()){
            model.addAttribute("content", "profile");
            model.addAttribute("isChangePassword", true);
            StudentDTO studentDTO = modelMapper.map(studentServiceImp.findById(student.getId()), StudentDTO.class);
            model.addAttribute("student", studentDTO);
            return "client";
        }
        if(!isCorrectPassword || !isConfirmPassword){
            if(!isCorrectPassword){
                model.addAttribute("errorOldPass", "Mật khẩu cũ không chính xác");
            }
            if(!isConfirmPassword){
                model.addAttribute("errorConfirm", "Xác nhận mật khẩu không khớp");
            }
            model.addAttribute("content", "profile");
            model.addAttribute("isChangePassword", true);
            StudentDTO studentDTO = modelMapper.map(studentServiceImp.findById(student.getId()), StudentDTO.class);
            model.addAttribute("student", studentDTO);
            return "client";
        }
        oldStudent.setPassword(passwordDTO.getNewPassword());
        studentServiceImp.updateStudent(oldStudent);
        return "redirect:/client/myProfile";
    }
}
