package com.eagleoj.web.controller.format.admin;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotNull;

/**
 * @author Smith
 **/
public class AddMailFormat {

    @NotNull
    private Boolean isOpenMail;

    @Length(min = 1, max = 255)
    private String host;

    @Range(min = 1)
    private Integer port;

    @Email(message = "发件人地址非法")
    private String username;

    @Length(min = 1, max = 255)
    private String password;

    @Email(message = "测试收件人地址非法")
    private String testMailAddress;

    public Boolean getOpenMail() {
        return isOpenMail;
    }

    public void setOpenMail(Boolean openMail) {
        isOpenMail = openMail;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTestMailAddress() {
        return testMailAddress;
    }

    public void setTestMailAddress(String testMailAddress) {
        this.testMailAddress = testMailAddress;
    }
}
