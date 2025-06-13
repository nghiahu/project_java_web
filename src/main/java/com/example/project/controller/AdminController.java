package com.example.project.controller;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.project.dto.CourseDTO;
import com.example.project.entity.Course;
import com.example.project.service.course.CourseServiceImp;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@Controller
@RequestMapping("admin")
public class AdminController {

    @Autowired
    private CourseServiceImp courseServiceImp;

    @Autowired
    private Cloudinary cloudinary;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("content", "statistics");
        return "dashboard";
    }
    @GetMapping("/student")
    public String student(Model model) {
        model.addAttribute("content", "student");
        return "dashboard";
    }
    @GetMapping("/course")
    public String course(Model model,
                         @RequestParam(defaultValue = "1") int page,
                         @RequestParam(defaultValue = "5") int size,
                         @RequestParam(defaultValue = "false") boolean isShowFrom,
                         @RequestParam(defaultValue = "false") boolean isShowFromEdit,
                         @RequestParam(required = false) Integer id) {
        model.addAttribute("content", "course");

        int totalCourse = courseServiceImp.countCourse();
        int totalPage = (int) Math.ceil((double) totalCourse / size);

        model.addAttribute("totalPage", totalPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("courses", courseServiceImp.listCourse(page, size));
        model.addAttribute("model", isShowFrom);
        model.addAttribute("isShowFromEdit", isShowFromEdit);

        CourseDTO courseDTO = new CourseDTO();

        if (isShowFromEdit && id != null) {
            Course course = courseServiceImp.getCourse(id);
            courseDTO = modelMapper.map(course, CourseDTO.class);
        }

        model.addAttribute("courseDTO", courseDTO);
        return "dashboard";
    }

    @GetMapping("/enrollment")
    public String enrollment(Model model) {
        model.addAttribute("content", "enrollment");
        return "dashboard";
    }

    @GetMapping("addCourse")
    public String addCourse(Model model) {
        model.addAttribute("courseDTO", new CourseDTO());
        return "managerCourse";
    }

    @PostMapping("addCourse-save")
    public String addCourseSave(@Valid @ModelAttribute("courseDTO") CourseDTO courseDTO, BindingResult bindingResult, Model model,
                                @RequestParam(defaultValue = "1") int page,
                                @RequestParam(defaultValue = "5") int size,
                                @RequestParam(defaultValue = "true") boolean isShowFrom,
                                @RequestParam(defaultValue = "false") boolean isShowFromEdit) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("content", "course");
            int totalCourse = courseServiceImp.countCourse();
            int totalPage = (int) Math.ceil((double) totalCourse / size);

            model.addAttribute("totalPage", totalPage);
            model.addAttribute("currentPage", page);
            model.addAttribute("courses", courseServiceImp.listCourse(page, size));
            model.addAttribute("model", isShowFrom);
            model.addAttribute("isShowFromEdit", isShowFromEdit);
            return "dashboard";
        }
        try {
            Map uploadResult = cloudinary.uploader().upload(courseDTO.getFile().getBytes(), ObjectUtils.emptyMap());
            String url = (String) uploadResult.get("url");
            if (url == null && url.isEmpty()) {
                throw  new RuntimeException("Invalid URL");
            }
            courseDTO.setImage(url);

            Course course = modelMapper.map(courseDTO, Course.class);
            courseServiceImp.saveCourse(course);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/admin/course";
    }

    @GetMapping("updateCourse")
    public String updateCourse(@RequestParam("id") int id, Model model) {
        Course course = courseServiceImp.getCourse(id);
        model.addAttribute("courseDTO", modelMapper.map(course, CourseDTO.class));
        return "updateCourse";
    }

    @PostMapping("editCourse-save")
    public String editCourseSave(@Valid @ModelAttribute("courseDTO") CourseDTO courseDTO, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "updateCourse";
        }
        try {
            Course course = modelMapper.map(courseDTO, Course.class);
            if (courseDTO.getFile() != null && !courseDTO.getFile().isEmpty()) {
                Map uploadResult = cloudinary.uploader().upload(courseDTO.getFile().getBytes(), ObjectUtils.emptyMap());
                String url = (String) uploadResult.get("url");
                if (url == null || url.isEmpty()) {
                    throw new RuntimeException("Upload lỗi");
                }
                course.setImage(url);
            } else {
                Course oldCourse = courseServiceImp.getCourse(course.getId());
                course.setImage(oldCourse.getImage());
            }

            courseServiceImp.saveCourse(course);

            return "redirect:/admin/course";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Đã xảy ra lỗi khi cập nhật sinh viên.");
            return "updateCourse";
        }
    }
    @GetMapping("deleteCourse")
    public String delete(@RequestParam("id") int id, Model model) {
        courseServiceImp.deleteCourse(id);
        return "redirect:/admin/course";
    }
}
