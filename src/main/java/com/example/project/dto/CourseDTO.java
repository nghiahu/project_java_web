package com.example.project.dto;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CourseDTO {
    private int id;
   @NotBlank(message = "Tên khóa học không được để trống")
    private String name;
    @NotNull(message = "Thời lượng không được để trống")
    @Min( value = 1, message = "Thời lượng phải lớn hơn 0")
    private Integer duration;
    @NotBlank(message = "Giảng viên phụ trách không được để trống")
    private String instructor;
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate create_at;
    private String image;
    private boolean status;

    private transient MultipartFile file;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getInstructor() {
        return instructor;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

    public String getCreate_at() {
        return create_at.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    public void setCreate_at(LocalDate create_at) {
        this.create_at = create_at;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
