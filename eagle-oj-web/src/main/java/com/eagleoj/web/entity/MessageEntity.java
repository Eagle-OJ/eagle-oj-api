package com.eagleoj.web.entity;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author Smith
 **/
public class MessageEntity {
    private Integer mid;

    private Integer owner;

    private Integer type;

    private String content;

    @JSONField(name = "json_content")
    private JSONObject jsonContent;

    @JSONField(name = "create_time")
    private Long createTime;

    public Integer getMid() {
        return mid;
    }

    public void setMid(Integer mid) {
        this.mid = mid;
    }

    public Integer getOwner() {
        return owner;
    }

    public void setOwner(Integer owner) {
        this.owner = owner;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public JSONObject getJsonContent() {
        return jsonContent;
    }

    public void setJsonContent(JSONObject jsonContent) {
        this.jsonContent = jsonContent;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }
}
