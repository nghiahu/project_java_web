package com.example.project.dto;

import javax.validation.constraints.NotBlank;

public class PasswordDTO {
    @NotBlank(message = "Mật khẩu cũ không được để trống")
    private String password;
    @NotBlank(message = "Mật khẩu mới không được để trống")
    private String newPassword;
    private String confirmPassword;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
