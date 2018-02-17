package com.eagleoj.web.controller.format.admin;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * @author Smith
 **/
public class UpdateGlobalSettingFormat {

    @NotNull
    @Length(min = 1, max = 20)
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
