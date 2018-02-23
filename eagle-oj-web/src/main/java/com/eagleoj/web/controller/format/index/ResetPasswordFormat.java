package com.eagleoj.web.controller.format.index;

import org.hibernate.validator.constraints.Email;

import javax.validation.constraints.NotNull;

/**
 * @author Smith
 **/
public class ResetPasswordFormat {

    @Email
    @NotNull
    private String email;

    @NotNull
    private String password;

    @NotNull
    private String code;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
