package com.eagleoj.web.entity;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author Smith
 **/
public class AttachmentEntity {
    private Integer aid;

    private Integer owner;

    private String url;

    private Integer status;

    @JSONField(name = "upload_time")
    private Long uploadTime;

    public Integer getAid() {
        return aid;
    }

    public void setAid(Integer aid) {
        this.aid = aid;
    }

    public Integer getOwner() {
        return owner;
    }

    public void setOwner(Integer owner) {
        this.owner = owner;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(Long uploadTime) {
        this.uploadTime = uploadTime;
    }
}
