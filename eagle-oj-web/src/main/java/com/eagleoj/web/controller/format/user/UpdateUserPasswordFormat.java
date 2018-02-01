package com.eagleoj.web.controller.format.user;

import com.alibaba.fastjson.annotation.JSONField;

import javax.validation.constraints.NotNull;

/**
 * @author Smith
 **/
public class UpdateUserPasswordFormat {

    @NotNull
    @JSONField(name = "origin_password")
    private String originPassword;

    @NotNull
    @JSONField(name = "new_password")
    private String newPassword;

    public String getOriginPassword() {
        return originPassword;
    }

    public void setOriginPassword(String originPassword) {
        this.originPassword = originPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
