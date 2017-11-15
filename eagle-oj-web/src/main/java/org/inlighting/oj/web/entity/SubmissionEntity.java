package org.inlighting.oj.web.entity;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.annotation.JSONField;
import org.inlighting.oj.judge.config.CodeLanguageEnum;
import org.inlighting.oj.judge.config.ProblemStatusEnum;

/**
 * @author Smith
 **/
public class SubmissionEntity {
    private int sid;

    private int owner;

    private int pid;

    @JSONField(name = "code_source")
    private int codeSource;

    @JSONField(name = "code_language")
    private CodeLanguageEnum codeLanguage;

    private int belong;

    private JSONArray result;

    @JSONField(name = "time_used")
    private double timeUsed;

    @JSONField(name = "memory_used")
    private double memoryUsed;

    private ProblemStatusEnum status;

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

    public int getCodeSource() {
        return codeSource;
    }

    public void setCodeSource(int codeSource) {
        this.codeSource = codeSource;
    }

    public CodeLanguageEnum getCodeLanguage() {
        return codeLanguage;
    }

    public void setCodeLanguage(CodeLanguageEnum codeLanguage) {
        this.codeLanguage = codeLanguage;
    }

    public int getBelong() {
        return belong;
    }

    public void setBelong(int belong) {
        this.belong = belong;
    }

    public JSONArray getResult() {
        return result;
    }

    public void setResult(JSONArray result) {
        this.result = result;
    }

    public double getTimeUsed() {
        return timeUsed;
    }

    public void setTimeUsed(double timeUsed) {
        this.timeUsed = timeUsed;
    }

    public double getMemoryUsed() {
        return memoryUsed;
    }

    public void setMemoryUsed(double memoryUsed) {
        this.memoryUsed = memoryUsed;
    }

    public ProblemStatusEnum getStatus() {
        return status;
    }

    public void setStatus(ProblemStatusEnum status) {
        this.status = status;
    }

    public long getSubmitTime() {
        return submitTime;
    }

    public void setSubmitTime(long submitTime) {
        this.submitTime = submitTime;
    }
}
