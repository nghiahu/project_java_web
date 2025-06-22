package com.example.project.controller;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.project.dto.CourseDTO;
import com.example.project.dto.CourseStatistics;
import com.example.project.entity.Course;
import com.example.project.service.course.CourseServiceImp;
import com.example.project.service.enrollment.EnrollmentServiceImp;
import com.example.project.service.student.StudentServiceImp;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("admin")
public class AdminController {

    @Autowired
    private CourseServiceImp courseServiceImp;

    @Autowired
    private StudentServiceImp studentServiceImp;

    @Autowired
    private EnrollmentServiceImp enrollmentServiceImp;

    @Autowired
    private Cloudinary cloudinary;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("totalCourse", courseServiceImp.countCourse());
        model.addAttribute("totalStudent", studentServiceImp.totalStudents());
        model.addAttribute("totalEnrollment", enrollmentServiceImp.countEnrollment());

        List<Course> courses = courseServiceImp.totalCourse();
        List<CourseStatistics> courseStatistics = new ArrayList<>();
        for (Course course : courses) {
            CourseStatistics courseStatistic = modelMapper.map(course, CourseStatistics.class);
            courseStatistic.setTotalStudents(enrollmentServiceImp.countStudentsCourse(course.getId()));
            courseStatistics.add(courseStatistic);
        }
        List<Course> courseTop = enrollmentServiceImp.getTopCourses();
        List<CourseStatistics> topCourseStatistic = new ArrayList<>();
        for (Course course : courseTop) {
            CourseStatistics courseStatistic = modelMapper.map(course, CourseStatistics.class);
            courseStatistic.setTotalStudents(enrollmentServiceImp.countStudentsCourse(course.getId()));
            topCourseStatistic.add(courseStatistic);
        }
        model.addAttribute("courseTop", topCourseStatistic);
        model.addAttribute("courseStatistic", courseStatistics);
        model.addAttribute("content", "dashboard");
        return "dashboard";
    }

    @GetMapping("/statistic")
    public String statistic(Model model) {
        model.addAttribute("totalCourse", courseServiceImp.countCourse());
        model.addAttribute("totalStudent", studentServiceImp.totalStudents());
        model.addAttribute("totalEnrollment", enrollmentServiceImp.countEnrollment());

        List<Course> courses = courseServiceImp.totalCourse();
        List<CourseStatistics> courseStatistics = new ArrayList<>();
        for (Course course : courses) {
            CourseStatistics courseStatistic = modelMapper.map(course, CourseStatistics.class);
            courseStatistic.setTotalStudents(enrollmentServiceImp.countStudentsCourse(course.getId()));
            courseStatistics.add(courseStatistic);
        }
        List<Course> courseTop = enrollmentServiceImp.getTopCourses();
        List<CourseStatistics> topCourseStatistic = new ArrayList<>();
        for (Course course : courseTop) {
            CourseStatistics courseStatistic = modelMapper.map(course, CourseStatistics.class);
            courseStatistic.setTotalStudents(enrollmentServiceImp.countStudentsCourse(course.getId()));
            topCourseStatistic.add(courseStatistic);
        }
        model.addAttribute("courseTop", topCourseStatistic);
        model.addAttribute("courseStatistic", courseStatistics);
        return "statistics";
    }

    @GetMapping("/course")
    public String course(Model model,
                         @RequestParam(defaultValue = "1") int page,
                         @RequestParam(defaultValue = "5") int size,
                         @RequestParam(defaultValue = "false") boolean isShowFrom,
                         @RequestParam(defaultValue = "false") boolean isShowFromEdit,
                         @RequestParam(defaultValue = "") String keyword,
                         @RequestParam(defaultValue = "false") boolean isConfirmDelete,
                         @RequestParam(required = false) Integer id,
                         @RequestParam(defaultValue = "") String sortBy,
                         @RequestParam(defaultValue = "false") boolean isConfirmRestore,
                         @RequestParam(defaultValue = "false") boolean isRestore,
                         @RequestParam(defaultValue = "false") boolean isDelete,
                         @RequestParam(required = false) Boolean status,
                         @RequestParam(defaultValue = "asc") String sortType) {
        model.addAttribute("content", "course");
        int totalCourse;
        if (isRestore) {
            courseServiceImp.reStore(id);
        }
        if(isDelete){
            courseServiceImp.deleteCourse(id);
        }
        if (!keyword.isEmpty()) {
            totalCourse = courseServiceImp.totalCourseSearch(keyword);
        }else if(status != null) {
            totalCourse = courseServiceImp.countFilterCourse(status);
        }else {
            totalCourse = courseServiceImp.countCourse();
        }
        int totalPage = (int) Math.ceil((double) totalCourse / size);

        String sortField = "id";
        String sortDirection = "asc";
        List<Course> courses;
        if (!sortBy.isEmpty() && sortBy.contains("-")) {
            String[] parts = sortBy.split("-");
            if (parts.length == 2) {
                sortField = parts[0];
                sortDirection = parts[1];
            }
        }
        if (!keyword.isEmpty()) {
            courses = courseServiceImp.searchCourse(keyword, page, size);
        } else if (status != null) {
            courses = courseServiceImp.filterCourse(status, page, size);
        } else if (!sortBy.isEmpty()) {
            courses = courseServiceImp.filCourse(null, sortField, sortDirection, page, size);
        } else {
            courses = courseServiceImp.listCourse(page, size);
        }

        List<CourseDTO> courseDTOs = courses.stream().map(course -> modelMapper.map(course, CourseDTO.class)).collect(Collectors.toList());
        model.addAttribute("courses", courseDTOs);
        model.addAttribute("totalPage", totalPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("model", isShowFrom);
        model.addAttribute("isShowFromEdit", isShowFromEdit);
        model.addAttribute("isConfirmDelete", isConfirmDelete);
        model.addAttribute("keyword", keyword);
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("sortType", sortType);
        model.addAttribute("isConfirmRestore", isConfirmRestore);
        model.addAttribute("status", status);
        CourseDTO courseDTO = new CourseDTO();
        if (isConfirmDelete && id != null) {
            model.addAttribute("id", id);
        }
        if(isConfirmRestore && id != null) {
            model.addAttribute("id", id);
        }
        if (isShowFromEdit && id != null) {
            Course course = courseServiceImp.getCourse(id);
            courseDTO = modelMapper.map(course, CourseDTO.class);
        }
        model.addAttribute("courseDTO", courseDTO);
        return "dashboard";
    }

    @GetMapping("addCourse")
    public String addCourse(Model model, @RequestParam(defaultValue = "") String keyword) {
        model.addAttribute("courseDTO", new CourseDTO());
        model.addAttribute("keyword", keyword);
        return "managerCourse";
    }

    @PostMapping("addCourse-save")
    public String addCourseSave(@Valid @ModelAttribute("courseDTO") CourseDTO courseDTO,
                                BindingResult bindingResult, Model model,
                                @RequestParam(defaultValue = "1") int page,
                                @RequestParam(defaultValue = "5") int size,
                                @RequestParam(defaultValue = "true") boolean isShowFrom,
                                @RequestParam(defaultValue = "false") boolean isShowFromEdit,
                                @RequestParam(defaultValue = "") String keyword,
                                @RequestParam(defaultValue = "false") boolean isConfirmDelete,
                                @RequestParam(required = false) Integer id,
                                @RequestParam(defaultValue = "") String sortBy,
                                @RequestParam(defaultValue = "false") boolean isConfirmRestore,
                                @RequestParam(defaultValue = "false") boolean isRestore,
                                @RequestParam(defaultValue = "false") boolean isDelete,
                                @RequestParam(required = false) Boolean status,
                                @RequestParam(defaultValue = "asc") String sortType) {
        boolean validateCourseName = courseServiceImp.validateCourseName(courseDTO.getName(), 0);
        if (bindingResult.hasErrors() || courseDTO.getFile().getSize() == 0 || !validateCourseName) {
            if(!validateCourseName) {
                model.addAttribute("errorName", "Tên khóa học đã tồn tại");
            }
            if(courseDTO.getFile().getSize() == 0) {
                model.addAttribute("fileError", "Ảnh không được để trống");
            }

            String sortField = "id";
            String sortDirection = "asc";
            List<Course> courses;
            if (!sortBy.isEmpty() && sortBy.contains("-")) {
                String[] parts = sortBy.split("-");
                if (parts.length == 2) {
                    sortField = parts[0];
                    sortDirection = parts[1];
                }
            }
            if (!keyword.isEmpty()) {
                courses = courseServiceImp.searchCourse(keyword, page, size);
            } else if (status != null) {
                courses = courseServiceImp.filterCourse(status, page, size);
            } else if (!sortBy.isEmpty()) {
                courses = courseServiceImp.filCourse(null, sortField, sortDirection, page, size);
            } else {
                courses = courseServiceImp.listCourse(page, size);
            }
            model.addAttribute("courses", courses);
            model.addAttribute("content", "course");
            int totalCourse;
            if (!keyword.isEmpty()) {
                totalCourse = courseServiceImp.totalCourseSearch(keyword);
            }else if(status != null) {
                totalCourse = courseServiceImp.countFilterCourse(status);
            }else {
                totalCourse = courseServiceImp.countCourse();
            }
            int totalPage = (int) Math.ceil((double) totalCourse / size);

            model.addAttribute("totalPage", totalPage);
            model.addAttribute("currentPage", page);
            model.addAttribute("model", isShowFrom);
            model.addAttribute("isShowFromEdit", isShowFromEdit);
            model.addAttribute("isConfirmDelete", isConfirmDelete);
            model.addAttribute("keyword", keyword);
            model.addAttribute("sortBy", sortBy);
            model.addAttribute("sortType", sortType);
            model.addAttribute("isConfirmRestore", isConfirmRestore);
            model.addAttribute("status", status);
            return "dashboard";
        }
        try {
            Map uploadResult = cloudinary.uploader().upload(courseDTO.getFile().getBytes(), ObjectUtils.emptyMap());
            String url = (String) uploadResult.get("url");
            if (url == null && url.isEmpty()) {
                throw  new RuntimeException("Invalid URL");
            }
            courseDTO.setImage(url);
            courseDTO.setStatus(true);
            Course course = modelMapper.map(courseDTO, Course.class);
            courseServiceImp.saveCourse(course);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/admin/course";
    }

    @GetMapping("updateCourse")
    public String updateCourse(@RequestParam("id") int id, Model model, @RequestParam(defaultValue = "") String keyword) {
        Course course = courseServiceImp.getCourse(id);
        model.addAttribute("courseDTO", modelMapper.map(course, CourseDTO.class));
        model.addAttribute("keyword", keyword);
        return "updateCourse";
    }

    @PostMapping("editCourse-save")
    public String editCourseSave(@Valid @ModelAttribute("courseDTO") CourseDTO courseDTO,
                                 BindingResult bindingResult, Model model,
                                 @RequestParam(defaultValue = "1") int page,
                                 @RequestParam(defaultValue = "5") int size,
                                 @RequestParam(defaultValue = "false") boolean isShowFrom,
                                 @RequestParam(defaultValue = "true") boolean isShowFromEdit,
                                 @RequestParam(defaultValue = "") String keyword,
                                 @RequestParam(defaultValue = "false") boolean isConfirmDelete,
                                 @RequestParam(required = false) Integer id,
                                 @RequestParam(defaultValue = "") String sortBy,
                                 @RequestParam(defaultValue = "false") boolean isConfirmRestore,
                                 @RequestParam(defaultValue = "false") boolean isRestore,
                                 @RequestParam(defaultValue = "false") boolean isDelete,
                                 @RequestParam(required = false) Boolean status,
                                 @RequestParam(defaultValue = "asc") String sortType) {
        boolean validateCourseName = courseServiceImp.validateCourseName(courseDTO.getName(), courseDTO.getId());
        if (bindingResult.hasErrors() || !validateCourseName) {
            if(!validateCourseName) {
                model.addAttribute("errorName", "Tên khóa học đã tồn tại");
            }
            Course oldCourse = courseServiceImp.getCourse(courseDTO.getId());
            courseDTO.setImage(oldCourse.getImage());
            String sortField = "id";
            String sortDirection = "asc";
            List<Course> courses;
            if (!sortBy.isEmpty() && sortBy.contains("-")) {
                String[] parts = sortBy.split("-");
                if (parts.length == 2) {
                    sortField = parts[0];
                    sortDirection = parts[1];
                }
            }
            if (!keyword.isEmpty()) {
                courses = courseServiceImp.searchCourse(keyword, page, size);
            } else if (status != null) {
                courses = courseServiceImp.filterCourse(status, page, size);
            } else if (!sortBy.isEmpty()) {
                courses = courseServiceImp.filCourse(null, sortField, sortDirection, page, size);
            } else {
                courses = courseServiceImp.listCourse(page, size);
            }
            model.addAttribute("courses", courses);
            model.addAttribute("content", "course");
            int totalCourse;
            if (!keyword.isEmpty()) {
                totalCourse = courseServiceImp.totalCourseSearch(keyword);
            }else if(status != null) {
                totalCourse = courseServiceImp.countFilterCourse(status);
            }else {
                totalCourse = courseServiceImp.countCourse();
            }
            int totalPage = (int) Math.ceil((double) totalCourse / size);

            model.addAttribute("totalPage", totalPage);
            model.addAttribute("currentPage", page);
            model.addAttribute("model", isShowFrom);
            model.addAttribute("isShowFromEdit", isShowFromEdit);
            model.addAttribute("isConfirmDelete", isConfirmDelete);
            model.addAttribute("keyword", keyword);
            model.addAttribute("sortBy", sortBy);
            model.addAttribute("sortType", sortType);
            model.addAttribute("isConfirmRestore", isConfirmRestore);
            model.addAttribute("status", status);
            return "dashboard";
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
            course.setStatus(true);
            courseServiceImp.saveCourse(course);

            return "redirect:/admin/course";
        } catch (Exception e) {
            e.printStackTrace();
            return "updateCourse";
        }
    }
    @GetMapping("openConfirm")
    public String delete(@RequestParam("id") int id, Model model, @RequestParam(defaultValue = "") String keyword) {
        model.addAttribute("id", id);
        model.addAttribute("keyword", keyword);
        return "formConfirmDel";
    }
}
