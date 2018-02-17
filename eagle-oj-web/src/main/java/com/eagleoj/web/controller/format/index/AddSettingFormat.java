package com.eagleoj.web.controller.format.index;

import com.alibaba.fastjson.annotation.JSONField;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * @author Smith
 **/
public class AddSettingFormat {

    @NotBlank
    @Length(max = 20)
    private String nickname;

    @NotNull
    @Email
    private String email;

    @NotNull
    private String password;

    @NotNull
    private String title;

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
