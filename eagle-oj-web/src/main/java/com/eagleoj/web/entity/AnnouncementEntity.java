package com.eagleoj.web.entity;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author Smith
 **/
public class AnnouncementEntity {
    private Integer aid;

    private String title;

    private String content;

    @JSONField(name = "create_time")
    private Long createTime;

    public Integer getAid() {
        return aid;
    }

    public void setAid(Integer aid) {
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

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }
}
