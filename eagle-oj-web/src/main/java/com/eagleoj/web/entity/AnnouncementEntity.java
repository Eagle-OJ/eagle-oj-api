package com.eagleoj.web.entity;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author Smith
 **/
public class AnnouncementEntity {
    private int aid;

    private String title;

    private String content;

    @JSONField(name = "create_time")
    private long createTime;

    public int getAid() {
        return aid;
    }

    public void setAid(int aid) {
        this.aid = aid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }
}
