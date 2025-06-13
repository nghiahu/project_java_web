package com.example.project.entity;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false, unique = true, columnDefinition = "varchar(50)")
    private String username;
    @Column(nullable = true, columnDefinition = "varchar(100)")
    private String name;
    @Column(nullable = false, columnDefinition = "date")
    private LocalDate dob;
    @Column(nullable = false, unique = true, columnDefinition = "varchar(100)")
    private String email;
    @Column(nullable = false, columnDefinition = "bit")
    private boolean sex;
    @Column(nullable = false, unique = true, columnDefinition = "varchar(20)")
    private String phone;
    @Column(nullable = false, columnDefinition = "varchar(255)")
    private String password;
    @Column(insertable = false, updatable = false, columnDefinition = "date default(now())")
    private LocalDate create_at;
    @Column(nullable = false, columnDefinition = "bit")
    private boolean role;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    private List<Enrollment> enrollments;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isSex() {
        return sex;
    }

    public void setSex(boolean sex) {
        this.sex = sex;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDate getCreate_at() {
        return create_at;
    }

    public void setCreate_at(LocalDate create_at) {
        this.create_at = create_at;
    }

    public boolean isRole() {
        return role;
    }

    public void setRole(boolean role) {
        this.role = role;
    }

    public List<Enrollment> getEnrollments() {
        return enrollments;
    }

    public void setEnrollments(List<Enrollment> enrollments) {
        this.enrollments = enrollments;
    }
}
