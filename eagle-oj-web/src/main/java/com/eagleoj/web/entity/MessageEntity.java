package com.eagleoj.web.entity;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author Smith
 **/
public class MessageEntity {
    private int mid;

    private int owner;

    private int type;

    private String content;

    @JSONField(name = "json_content")
    private JSONObject jsonContent;

    @JSONField(name = "create_time")
    private long createTime;

    public int getMid() {
        return mid;
    }

    public void setMid(int mid) {
        this.mid = mid;
    }

    public int getOwner() {
        return owner;
    }

    public void setOwner(int owner) {
        this.owner = owner;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
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

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }
}
