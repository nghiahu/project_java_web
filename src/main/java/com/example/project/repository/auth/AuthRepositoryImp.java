package com.example.project.repository.auth;

import com.example.project.entity.Student;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class AuthRepositoryImp implements AuthRepository {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void register(Student student) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.saveOrUpdate(student);
        session.getTransaction().commit();
        session.close();
    }
    @Override
    public Student login(String email, String password) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Student student = session.createQuery("from Student where email =:email", Student.class).setParameter("email", email).uniqueResult();
        if (student != null && student.getPassword().equals(password)) {
            session.getTransaction().commit();
            session.close();
            return student;
        }
        session.close();
        return null;
    }

    @Override
    public boolean validateUsername(String username) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Student student = session.createQuery("from Student where username =:username", Student.class).setParameter("username", username).uniqueResult();
        session.getTransaction().commit();
        session.close();
        if(student != null) {
            return false;
        }
        return true;
    }

    @Override
    public boolean validatePhoneNumber(String phoneNumber, int id) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Student student = null;
        if(id <= 0){
            student = session.createQuery("from Student where phone =: phoneNumber", Student.class).setParameter("phoneNumber", phoneNumber).uniqueResult();
        }else {
            student = session.createQuery("from Student where phone =: phoneNumber and id !=:id", Student.class)
                    .setParameter("phoneNumber", phoneNumber)
                    .setParameter("id", id)
                    .uniqueResult();
        }
        session.getTransaction().commit();
        session.close();
        if(student != null) {
            return false;
        }
        return true;
    }

    @Override
    public boolean validateEmail(String email, int id) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Student student = null;
        if(id <= 0){
            student = session.createQuery("from Student where email =: email", Student.class).setParameter("email", email).uniqueResult();
        }else {
            student = session.createQuery("from Student where email =: email and id !=:id", Student.class)
                    .setParameter("email", email)
                    .setParameter("id", id)
                    .uniqueResult();
        }
        session.getTransaction().commit();
        session.close();
        if(student != null) {
            return false;
        }
        return true;
    }
}
