package com.example.project.service.enrollment;

import com.example.project.entity.Course;
import com.example.project.entity.Enrollment;
import com.example.project.model.EnrollmentAd;
import com.example.project.model.StatusEnrollment;

import java.util.List;

public interface EnrollmentService {
    boolean isRegistered(int courseId, int studentId);
    void saveEnrollment(Enrollment enrollment);
    List<Enrollment> getEnrollmentsByStd(int std_id, int page, int size);
    int countEnrollmentsByStd(int std_id);
    List<Enrollment> searchEnrollmentByCourse(int std_id, String keyword, int page, int size);
    int countEnrollmentByCourse(int std_id, String keyword);
    List<Enrollment> sortEnrollment(int std_id,String sortType, int page, int size);
    int countEnrollment();
    int countStudentsCourse(int courseId);
    List<Course> getTopCourses();
    List<EnrollmentAd> getEnrollmentAd(int page, int size);
    int countEnrollmentAd();
    List<EnrollmentAd> searchEnrollmentAd(String keyword, int page, int size);
    int countSearchEnrollmentAd(String keyword);
    List<EnrollmentAd> filterEnrollmentAd(StatusEnrollment statusEnrollment, int page, int size);
    int countFilterEnrollmentAd(StatusEnrollment statusEnrollment);
    void confirmEnrollment(int enrollmentId);
    void deniedEnrollment(int enrollmentId);
    void cancelEnrollment(int enrollmentId);
}
