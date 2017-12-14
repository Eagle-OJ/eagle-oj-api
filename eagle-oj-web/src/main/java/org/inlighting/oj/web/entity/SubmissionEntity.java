package org.inlighting.oj.web.entity;

import com.alibaba.fastjson.annotation.JSONField;
import org.inlighting.oj.judge.LanguageEnum;
import org.inlighting.oj.judge.ResultEnum;

/**
 * @author Smith
 **/
public class SubmissionEntity {
    private int sid;

    private int owner;

    private int pid;

    private int cid;

    @JSONField(name = "source_code")
    private int sourceCode;

    @JSONField(name = "lang")
    private LanguageEnum lang;

    @JSONField(name = "time")
    private double time;

    @JSONField(name = "memory")
    private int memory;

    private ResultEnum status;

    @JSONField(name = "submit_time")
    private long submitTime;

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public int getOwner() {
        return owner;
    }

    public void setOwner(int owner) {
        this.owner = owner;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public int getSourceCode() {
        return sourceCode;
    }

    public void setSourceCode(int sourceCode) {
        this.sourceCode = sourceCode;
    }

    public LanguageEnum getLang() {
        return lang;
    }

    public void setLang(LanguageEnum lang) {
        this.lang = lang;
    }

    public double getTime() {
        return time;
    }

    public void setTime(double time) {
        this.time = time;
    }

    public int getMemory() {
        return memory;
    }

    public void setMemory(int memory) {
        this.memory = memory;
    }

    public ResultEnum getStatus() {
        return status;
    }

    public void setStatus(ResultEnum status) {
        this.status = status;
    }

    public long getSubmitTime() {
        return submitTime;
    }

    public void setSubmitTime(long submitTime) {
        this.submitTime = submitTime;
    }
}
