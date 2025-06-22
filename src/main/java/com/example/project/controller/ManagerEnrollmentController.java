package com.example.project.controller;

import com.example.project.model.EnrollmentAd;
import com.example.project.model.StatusEnrollment;
import com.example.project.service.enrollment.EnrollmentServiceImp;
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
public class ManagerEnrollmentController {

    @Autowired
    private EnrollmentServiceImp enrollmentServiceImp;

    @GetMapping("/enrollment")
    public String enrollment(Model model,
                             @RequestParam(defaultValue = "1") int page,
                             @RequestParam(defaultValue = "5") int size,
                             @RequestParam(defaultValue = "") String keyword,
                             @RequestParam(required = false) StatusEnrollment selectBy,
                             @RequestParam(defaultValue = "false") boolean isConfirm,
                             @RequestParam(defaultValue = "false") boolean isDenied,
                             @RequestParam(defaultValue = "false") boolean confirm,
                             @RequestParam(defaultValue = "false") boolean denied,
                             @RequestParam(required = false) Integer id) {
        List<EnrollmentAd> enrollmentAds = new ArrayList<>();

        if(confirm){
            enrollmentServiceImp.confirmEnrollment(id);
        }
        if(denied){
            enrollmentServiceImp.deniedEnrollment(id);
        }

        int totalEnrollments = 0;
        if(!keyword.isEmpty()){
            enrollmentAds = enrollmentServiceImp.searchEnrollmentAd(keyword, page, size);
            totalEnrollments = enrollmentServiceImp.countSearchEnrollmentAd(keyword);
        }else if(selectBy != null){
            enrollmentAds = enrollmentServiceImp.filterEnrollmentAd(selectBy , page, size);
            totalEnrollments = enrollmentServiceImp.countFilterEnrollmentAd(selectBy);
        }else {
            enrollmentAds = enrollmentServiceImp.getEnrollmentAd(page, size);
            totalEnrollments = enrollmentServiceImp.countEnrollmentAd();
        }
        int totalPages = (int) Math.ceil((double) totalEnrollments / (double) size);
        model.addAttribute("statusList", StatusEnrollment.values());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPage", totalPages);
        model.addAttribute("enrollmentAds", enrollmentAds);
        model.addAttribute("keyword", keyword);
        model.addAttribute("selectBy", selectBy);
        model.addAttribute("content", "enrollment");
        model.addAttribute("isConfirm", isConfirm);
        model.addAttribute("isDenied", isDenied);
        model.addAttribute("id", id);
        return "dashboard";
    }
    @GetMapping("/enrollmentAd")
    public String enrollmentAd(Model model,
                               @RequestParam(defaultValue = "1") int page,
                               @RequestParam(defaultValue = "5") int size,
                               @RequestParam(defaultValue = "") String keyword,
                               @RequestParam(required = false) String selectBy){
        List<EnrollmentAd> enrollmentAds = enrollmentServiceImp.getEnrollmentAd(page, size);
        int totalEnrollments = enrollmentServiceImp.countEnrollmentAd();
        int totalPages = (int) Math.ceil((double) totalEnrollments / (double) size);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPage", totalPages);
        model.addAttribute("enrollmentAds", enrollmentAds);
        model.addAttribute("keyword", keyword);
        model.addAttribute("selectBy", selectBy);
        return "managerEnrollments";
    }
}
