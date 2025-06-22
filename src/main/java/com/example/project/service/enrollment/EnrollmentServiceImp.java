package com.example.project.service.enrollment;

import com.example.project.entity.Course;
import com.example.project.entity.Enrollment;
import com.example.project.model.EnrollmentAd;
import com.example.project.model.StatusEnrollment;
import com.example.project.repository.enrollment.EnrollmentRepositoryImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EnrollmentServiceImp implements EnrollmentService {

    @Autowired
    private EnrollmentRepositoryImp enrollmentRepositoryImp;

    @Override
    public boolean isRegistered(int courseId, int studentId) {
        return enrollmentRepositoryImp.isRegistered(courseId, studentId);
    }

    @Override
    public void saveEnrollment(Enrollment enrollment) {
        enrollmentRepositoryImp.saveEnrollment(enrollment);
    }

    @Override
    public List<Enrollment> getEnrollmentsByStd(int std_id, int page, int size) {
        return enrollmentRepositoryImp.getEnrollmentsByStd(std_id, page, size);
    }

    @Override
    public int countEnrollmentsByStd(int std_id) {
        return enrollmentRepositoryImp.countEnrollmentsByStd(std_id);
    }

    @Override
    public List<Enrollment> searchEnrollmentByCourse(int std_id, String keyword, int page, int size) {
        return enrollmentRepositoryImp.searchEnrollmentByCourse(std_id, keyword, page, size);
    }

    @Override
    public int countEnrollmentByCourse(int std_id, String keyword) {
        return enrollmentRepositoryImp.countEnrollmentByCourse(std_id, keyword);
    }

    @Override
    public List<Enrollment> sortEnrollment(int std_id, String sortType, int page, int size) {
        return enrollmentRepositoryImp.sortEnrollment(std_id, sortType, page, size);
    }

    @Override
    public int countEnrollment() {
        return enrollmentRepositoryImp.countEnrollment();
    }

    @Override
    public int countStudentsCourse(int courseId) {
        return enrollmentRepositoryImp.countStudentsCourse(courseId);
    }

    @Override
    public List<Course> getTopCourses() {
        return enrollmentRepositoryImp.getTopCourses();
    }

    @Override
    public List<EnrollmentAd> getEnrollmentAd(int page, int size) {
        return enrollmentRepositoryImp.getEnrollmentAd(page, size);
    }

    @Override
    public int countEnrollmentAd() {
        return enrollmentRepositoryImp.countEnrollmentAd();
    }

    @Override
    public List<EnrollmentAd> searchEnrollmentAd(String keyword, int page, int size) {
        return enrollmentRepositoryImp.searchEnrollmentAd(keyword, page, size);
    }

    @Override
    public int countSearchEnrollmentAd(String keyword) {
        return enrollmentRepositoryImp.countSearchEnrollmentAd(keyword);
    }

    @Override
    public List<EnrollmentAd> filterEnrollmentAd(StatusEnrollment statusEnrollment, int page, int size) {
        return enrollmentRepositoryImp.filterEnrollmentAd(statusEnrollment, page, size);
    }

    @Override
    public int countFilterEnrollmentAd(StatusEnrollment statusEnrollment) {
        return enrollmentRepositoryImp.countFilterEnrollmentAd(statusEnrollment);
    }

    @Override
    public void confirmEnrollment(int enrollmentId) {
        enrollmentRepositoryImp.confirmEnrollment(enrollmentId);
    }

    @Override
    public void deniedEnrollment(int enrollmentId) {
        enrollmentRepositoryImp.deniedEnrollment(enrollmentId);
    }

    @Override
    public void cancelEnrollment(int enrollmentId) {
        enrollmentRepositoryImp.cancelEnrollment(enrollmentId);
    }
}
