package com.example.project.repository.student;

import com.example.project.entity.Student;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class StudentRepositoryImp implements StudentRepository {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<Student> findAll(int page, int size) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Query query = session.createQuery("from Student");
        query.setFirstResult((page - 1) * size);
        query.setMaxResults(size);
        List<Student> students = query.list();
        session.getTransaction().commit();
        session.close();
        return students;
    }

    @Override
    public Student findById(int id) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Student student = (Student) session.get(Student.class, id);
        session.getTransaction().commit();
        session.close();
        return student;
    }

    @Override
    public void lockStudent(int id) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Student student = (Student) session.get(Student.class, id);
        student.setStatus(!student.isStatus());
        session.update(student);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public int totalStudents() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Query query = session.createQuery("select count(*) from Student where role = false");
        query.setMaxResults(1);
        Long count = (Long) query.uniqueResult();
        session.getTransaction().commit();
        session.close();
        return count.intValue();
    }

    @Override
    public List<Student> sortStudent(String sortBy, String sortTYpe, int page, int size) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        String hql = "from Student where role = false order by " + sortBy + " " + sortTYpe;
        Query query = session.createQuery(hql);
        query.setFirstResult((page - 1) * size);
        query.setMaxResults(size);
        List<Student> students = query.list();
        session.getTransaction().commit();
        session.close();
        return students;
    }

    @Override
    public List<Student> getAllStudents(int page, int size) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Query query = session.createQuery("from Student where role = false ");
        query.setFirstResult((page - 1) * size);
        query.setMaxResults(size);
        List<Student> students = query.list();
        session.getTransaction().commit();
        session.close();
        return students;
    }

    @Override
    public List<Student> searchStudent(String keyword, int page, int size) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        Query query;
        List<Student> students;
        try {
            Integer idKey = Integer.parseInt(keyword);
            String hql = "from Student where (id = :idKey or name like :keyword or email like :keyword) and role = false";
            query = session.createQuery(hql);
            query.setParameter("idKey", idKey);
            query.setParameter("keyword", "%" + keyword + "%");
        } catch (NumberFormatException e) {
            String hql = "from Student where (name like :keyword or email like :keyword) and role = false";
            query = session.createQuery(hql);
            query.setParameter("keyword", "%" + keyword + "%");
        }

        query.setFirstResult((page - 1) * size);
        query.setMaxResults(size);
        students = query.list();

        session.getTransaction().commit();
        session.close();
        return students;
    }



    @Override
    public int searchStudentCount(String keyword) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Query query;
        try {
            Integer idKey = Integer.parseInt(keyword);
            String hql = "select  count(*) from Student where (id = :idKey or name like :keyword or email like :keyword) and role = false";
            query = session.createQuery(hql);
            query.setParameter("idKey", idKey);
            query.setParameter("keyword", "%" + keyword + "%");
        } catch (NumberFormatException e) {
            String hql = "select count(*) from Student where (name like :keyword or email like :keyword) and role = false";
            query = session.createQuery(hql);
            query.setParameter("keyword", "%" + keyword + "%");
        }
        Long count = (Long) query.uniqueResult();
        session.getTransaction().commit();
        session.close();
        return count.intValue();
    }

    @Override
    public void updateStudent(Student student) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.update(student);
        session.getTransaction().commit();
        session.close();
    }
}
