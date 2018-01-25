package com.eagleoj.web.entity;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author Smith
 **/
public class AttachmentEntity {
    private int aid;

    private int owner;

    private String url;

    private int status;

    @JSONField(name = "upload_time")
    private long uploadTime;

    public int getAid() {
        return aid;
    }

    public void setAid(int aid) {
        this.aid = aid;
    }

    public int getOwner() {
        return owner;
    }

    public void setOwner(int owner) {
        this.owner = owner;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(long uploadTime) {
        this.uploadTime = uploadTime;
    }
}
