package com.example.project.repository.enrollment;

import com.example.project.entity.Course;
import com.example.project.entity.Enrollment;
import com.example.project.model.EnrollmentAd;
import com.example.project.model.StatusEnrollment;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class EnrollmentRepositoryImp implements EnrollmentRepository {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public boolean isRegistered(int courseId, int studentId) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Enrollment enrollment = (Enrollment) session.createQuery("from Enrollment where course.id = :courseId and student.id = :studentId")
                .setParameter("courseId", courseId)
                .setParameter("studentId", studentId)
                .uniqueResult();
        session.getTransaction().commit();
        session.close();
        if (enrollment != null) {
            StatusEnrollment status = enrollment.getStatus();
            return status == StatusEnrollment.WAITING || status == StatusEnrollment.CONFIRMED;
        }
        return false;
    }

    @Override
    public void saveEnrollment(Enrollment enrollment) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.saveOrUpdate(enrollment);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public List<Enrollment> getEnrollmentsByStd(int std_id, int page, int size) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Query query = session.createQuery("from Enrollment where student.id = :std_id")
                .setParameter("std_id", std_id)
                .setFirstResult((page - 1) * size)
                .setMaxResults(size);
        List<Enrollment> enrollments = query.list();
        session.getTransaction().commit();
        session.close();
        return enrollments;
    }

    @Override
    public int countEnrollmentsByStd(int std_id) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Query query = session.createQuery("select count(*) from Enrollment where student.id = :std_id")
                .setParameter("std_id", std_id)
                .setMaxResults(1);
        Long count = (Long) query.uniqueResult();
        session.getTransaction().commit();
        session.close();
        return count.intValue();
    }

    @Override
    public List<Enrollment> searchEnrollmentByCourse(int std_id, String keyword, int page, int size) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Query query = session.createQuery("from Enrollment where student.id = :std_id and course.name like :keyword")
                .setParameter("std_id", std_id)
                .setParameter("keyword","%" + keyword +"%")
                .setFirstResult((page - 1) * size)
                .setMaxResults(size);
        List<Enrollment> enrollments = query.list();
        session.getTransaction().commit();
        session.close();
        return enrollments;
    }

    @Override
    public int countEnrollmentByCourse(int std_id, String keyword) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Query query = session.createQuery("select count(*) from Enrollment where student.id = :std_id and course.name = :keyword")
                .setParameter("std_id", std_id)
                .setParameter("keyword", keyword)
                .setMaxResults(1);
        Long count = (Long) query.uniqueResult();
        session.getTransaction().commit();
        return count.intValue();
    }

    @Override
    public List<Enrollment> sortEnrollment(int std_id, String sortType, int page, int size) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        String hql = "from Enrollment where student.id = :std_id order by status " + sortType;
        Query query = session.createQuery(hql);
        query.setParameter("std_id", std_id);
        query.setFirstResult((page - 1) * size)
                .setMaxResults(size);
        List<Enrollment> enrollments = query.list();
        session.getTransaction().commit();
        session.close();
        return enrollments;
    }

    @Override
    public int countEnrollment() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Query query = session.createQuery("select count(*) from Enrollment");
        Long count = (Long) query.uniqueResult();
        session.getTransaction().commit();
        session.close();
        return count.intValue();
    }

    @Override
    public int countStudentsCourse(int courseId) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Query query = session.createQuery("select count(*) from Enrollment where course.id = :course_id and status =: status");
        query.setParameter("course_id", courseId);
        query.setParameter("status",StatusEnrollment.CONFIRMED);
        Long count = (Long) query.uniqueResult();
        session.getTransaction().commit();
        session.close();
        return count.intValue();
    }

    @Override
    public List<Course> getTopCourses() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        Query<Course> query = session.createQuery(
                "select e.course from Enrollment e where e.status = :status group by e.course order by count(e.course) desc",
                Course.class
        );
        query.setParameter("status", StatusEnrollment.CONFIRMED);
        query.setMaxResults(5);

        List<Course> courses = query.list();

        session.getTransaction().commit();
        session.close();
        return courses;
    }

    @Override
    public List<EnrollmentAd> getEnrollmentAd(int page, int size) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Query<Enrollment> query = session.createQuery("from Enrollment", Enrollment.class)
                .setFirstResult((page - 1) * size)
                .setMaxResults(size);
        List<Enrollment> enrollments = query.list();

        List<EnrollmentAd> ads = enrollments.stream().map(e -> {
            EnrollmentAd ad = new EnrollmentAd();
            ad.setId(e.getId());
            ad.setStudentName(e.getStudent().getName());
            ad.setCourseName(e.getCourse().getName());
            ad.setRegistrationDate(e.getRegistered_at().toLocalDate());
            ad.setStatusEnrollment(e.getStatus());
            return ad;
        }).collect(Collectors.toList());
        session.getTransaction().commit();
        session.close();
        return ads;
    }

    @Override
    public int countEnrollmentAd() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Query query = session.createQuery("select count(*) from Enrollment");
        Long count = (Long) query.uniqueResult();
        session.getTransaction().commit();
        session.close();
        return count.intValue();
    }

    @Override
    public List<EnrollmentAd> searchEnrollmentAd(String keyword, int page, int size) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Query query = session.createQuery("from Enrollment where course.name like :keyword").setParameter("keyword","%" + keyword + "%")
                .setFirstResult((page - 1) * size)
                .setMaxResults(size);
        List<Enrollment> enrollments = query.list();

        List<EnrollmentAd> ads = enrollments.stream().map(e -> {
            EnrollmentAd ad = new EnrollmentAd();
            ad.setId(e.getId());
            ad.setStudentName(e.getStudent().getName());
            ad.setCourseName(e.getCourse().getName());
            ad.setRegistrationDate(e.getRegistered_at().toLocalDate());
            ad.setStatusEnrollment(e.getStatus());
            return ad;
        }).collect(Collectors.toList());
        session.getTransaction().commit();
        session.close();
        return ads;
    }

    @Override
    public int countSearchEnrollmentAd(String keyword) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Query query = session.createQuery("select count(*) from Enrollment where course.name like :keyword").setParameter("keyword","%" + keyword + "%")
                .setMaxResults(1);
        Long count = (Long) query.uniqueResult();
        session.getTransaction().commit();
        session.close();
        return count.intValue();
    }

    @Override
    public List<EnrollmentAd> filterEnrollmentAd(StatusEnrollment statusEnrollment, int page, int size) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Query query = session.createQuery("from Enrollment where status = :status")
                .setParameter("status", statusEnrollment)
                .setFirstResult((page - 1) * size)
                .setMaxResults(size);
        List<Enrollment> enrollments = query.list();
        List<EnrollmentAd> ads = enrollments.stream().map(e -> {
            EnrollmentAd ad = new EnrollmentAd();
            ad.setId(e.getId());
            ad.setStudentName(e.getStudent().getName());
            ad.setCourseName(e.getCourse().getName());
            ad.setRegistrationDate(e.getRegistered_at().toLocalDate());
            ad.setStatusEnrollment(e.getStatus());
            return ad;
        }).collect(Collectors.toList());
        session.getTransaction().commit();
        session.close();
        return ads;
    }

    @Override
    public int countFilterEnrollmentAd(StatusEnrollment statusEnrollment) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Query query = session.createQuery("select count(*) from Enrollment where status = :status")
                .setParameter("status", statusEnrollment)
                .setMaxResults(1);
        Long count = (Long) query.uniqueResult();
        session.getTransaction().commit();
        session.close();
        return count.intValue();
    }

    @Override
    public void confirmEnrollment(int enrollmentId) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Enrollment enrollment = session.get(Enrollment.class, enrollmentId);
        enrollment.setStatus(StatusEnrollment.CONFIRMED);
        session.update(enrollment);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void deniedEnrollment(int enrollmentId) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Enrollment enrollment = session.get(Enrollment.class, enrollmentId);
        enrollment.setStatus(StatusEnrollment.DENIED);
        session.update(enrollment);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void cancelEnrollment(int enrollmentId) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Enrollment enrollment = session.get(Enrollment.class, enrollmentId);
        enrollment.setStatus(StatusEnrollment.CANCEL);
        session.update(enrollment);
        session.getTransaction().commit();
        session.close();
    }


}
