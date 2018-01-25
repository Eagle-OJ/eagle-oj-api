package com.eagleoj.web.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.eagleoj.judge.LanguageEnum;
import com.eagleoj.judge.ResultEnum;

/**
 * @author Smith
 **/
public class SubmissionEntity {
    private Integer sid;

    private Integer owner;

    private Integer pid;

    private Integer cid;

    @JSONField(name = "source_code")
    private Integer sourceCode;

    @JSONField(name = "lang")
    private LanguageEnum lang;

    @JSONField(name = "time")
    private Double time;

    @JSONField(name = "memory")
    private Integer memory;

    private ResultEnum status;

    @JSONField(name = "submit_time")
    private Long submitTime;

    public Integer getSid() {
        return sid;
    }

    public void setSid(Integer sid) {
        this.sid = sid;
    }

    public Integer getOwner() {
        return owner;
    }

    public void setOwner(Integer owner) {
        this.owner = owner;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public Integer getSourceCode() {
        return sourceCode;
    }

    public void setSourceCode(Integer sourceCode) {
        this.sourceCode = sourceCode;
    }

    public LanguageEnum getLang() {
        return lang;
    }

    public void setLang(LanguageEnum lang) {
        this.lang = lang;
    }

    public Double getTime() {
        return time;
    }

    public void setTime(Double time) {
        this.time = time;
    }

    public Integer getMemory() {
        return memory;
    }

    public void setMemory(Integer memory) {
        this.memory = memory;
    }

    public ResultEnum getStatus() {
        return status;
    }

    public void setStatus(ResultEnum status) {
        this.status = status;
    }

    public Long getSubmitTime() {
        return submitTime;
    }

    public void setSubmitTime(Long submitTime) {
        this.submitTime = submitTime;
    }
}
