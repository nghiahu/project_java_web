package com.example.project.repository.course;

import com.example.project.entity.Course;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CourseRepositoryImp implements CourseRepository {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<Course> listCourse(int page, int size) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Query query = session.createQuery("from Course");
        query.setFirstResult((page - 1) * size);
        query.setMaxResults(size);
        List<Course> courses = query.list();
        session.getTransaction().commit();
        session.close();
        return courses;
    }

    @Override
    public Course getCourse(int id) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Query query = session.createQuery("from Course where id=:id");
        query.setParameter("id", id);
        Course course = (Course) query.uniqueResult();
        session.getTransaction().commit();
        session.close();
        return course;
    }

    @Override
    public void saveCourse(Course course) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.saveOrUpdate(course);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void deleteCourse(int id) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Query query = session.createQuery("delete from Course where id=:id");
        query.setParameter("id", id);
        query.executeUpdate();
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public int countCourse() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Query query = session.createQuery("select count(*) from Course");
        query.setMaxResults(1);
        Long count = (Long) query.uniqueResult();
        session.getTransaction().commit();
        session.close();
        return count.intValue();
    }
}
