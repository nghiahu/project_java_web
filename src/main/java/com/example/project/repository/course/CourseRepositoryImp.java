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
        Query query = session.createQuery("from Course where status = true");
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
        Course course = session.get(Course.class, id);
        course.setStatus(false);
        session.update(course);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public int countCourse() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Query query = session.createQuery("select count(*) from Course where status = true");
        query.setMaxResults(1);
        Long count = (Long) query.uniqueResult();
        session.getTransaction().commit();
        session.close();
        return count.intValue();
    }

    @Override
    public boolean validateCourseName(String courseName, int courseId) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Course course = new Course();
        if(courseId <= 0){
            course = session.createQuery("from Course where name =: courseName", Course.class).setParameter("courseName", courseName).uniqueResult();
        }else {
            course = session.createQuery("from Course where name =: courseName and id !=: courseId", Course.class)
                    .setParameter("courseName", courseName)
                    .setParameter("courseId", courseId).uniqueResult();
        }
        session.getTransaction().commit();
        session.close();
        if(course == null){
            return true;
        }
        return false;
    }

    @Override
    public List<Course> searchCourse(String courseName, int page, int size) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Query query = session.createQuery("from Course where name like :courseName", Course.class)
                .setParameter("courseName", "%" + courseName + "%")
                .setFirstResult((page - 1) * size)
                .setMaxResults(size);
        List<Course> courses = query.list();
        session.getTransaction().commit();
        session.close();
        return courses;
    }

    @Override
    public List<Course> filCourse(String filter, String sortBy, String sortTYpe, int page, int size) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        String hql = "from Course order by " + sortBy + " " + sortTYpe;
        Query query = session.createQuery(hql);
        query.setFirstResult((page - 1) * size);
        query.setMaxResults(size);
        List<Course> courses = query.list();
        session.getTransaction().commit();
        session.close();
        return courses;
    }

    @Override
    public int totalCourseSearch(String keyword) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Query query = session.createQuery("select count(*) from Course where name like :keyword");
        query.setParameter("keyword", "%" + keyword + "%");
        Long count = (Long) query.uniqueResult();
        session.getTransaction().commit();
        session.close();
        return count.intValue();
    }

    @Override
    public List<Course> totalCourse() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Query query = session.createQuery("from Course");
        List<Course> courses = query.list();
        session.getTransaction().commit();
        session.close();
        return courses;
    }

    @Override
    public void reStore(int id) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Course course = session.get(Course.class, id);
        course.setStatus(true);
        session.update(course);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public List<Course> filterCourse(boolean status, int page, int size) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Query query = session.createQuery("from Course where status =: status");
        query.setParameter("status", status);
        query.setFirstResult((page - 1) * size);
        query.setMaxResults(size);
        List<Course> courses = query.list();
        session.getTransaction().commit();
        session.close();
        return courses;
    }

    @Override
    public int countFilterCourse(boolean status) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Query query = session.createQuery("select count(*) from Course where status = :status");
        query.setParameter("status", status);
        Long count = (Long) query.uniqueResult();
        session.getTransaction().commit();
        session.close();
        return count.intValue();
    }

    @Override
    public List<Course> searchCourseClient(String courseName, int page, int size) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Query query = session.createQuery("from Course where name like :courseName and status = true", Course.class)
                .setParameter("courseName", "%" + courseName + "%")
                .setFirstResult((page - 1) * size)
                .setMaxResults(size);
        List<Course> courses = query.list();
        session.getTransaction().commit();
        session.close();
        return courses;
    }

    @Override
    public int countSearchCourseClient(String keyword) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Query query = session.createQuery("select count(*) from Course where name like :keyword and status = true");
        query.setParameter("keyword", "%" + keyword + "%");
        Long count = (Long) query.uniqueResult();
        session.getTransaction().commit();
        session.close();
        return count.intValue();
    }
}
