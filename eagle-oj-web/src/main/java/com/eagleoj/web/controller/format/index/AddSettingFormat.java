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

    @NotNull
    private String accessKey;

    @NotNull
    private String secretKey;

    @NotNull
    private String endPoint;

    @NotNull
    private String bucket;

    @NotNull
    private String url;

    @JSONField(name = "judger_url")
    @NotNull
    private String judgerUrl;

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

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(String endPoint) {
        this.endPoint = endPoint;
    }

    public String getBucket() {
        return bucket;
    }

    public void setBucket(String bucket) {
        this.bucket = bucket;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getJudgerUrl() {
        return judgerUrl;
    }

    public void setJudgerUrl(String judgerUrl) {
        this.judgerUrl = judgerUrl;
    }
}
