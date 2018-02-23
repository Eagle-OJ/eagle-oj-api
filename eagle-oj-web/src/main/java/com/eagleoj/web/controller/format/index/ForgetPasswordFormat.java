package com.eagleoj.web.controller.format.index;

import org.hibernate.validator.constraints.Email;

import javax.validation.constraints.NotNull;

/**
 * @author Smith
 **/
public class ForgetPasswordFormat {

    @NotNull
    private String url;

    @Email
    @NotNull
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
