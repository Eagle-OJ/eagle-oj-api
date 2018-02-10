package com.eagleoj.web.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.eagleoj.judge.ResultEnum;

/**
 * @author Smith
 **/
public class ContestProblemUserEntity {
    private Integer cid;

    private Integer pid;

    private Integer uid;

    @JSONField(name = "wrong_times")
    private Integer wrongTimes;

    private Integer score;

    private ResultEnum status;

    private Long solvedTime;

    private Long usedTime;

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public ResultEnum getStatus() {
        return status;
    }

    public void setStatus(ResultEnum status) {
        this.status = status;
    }

    public Long getSolvedTime() {
        return solvedTime;
    }

    public void setSolvedTime(Long solvedTime) {
        this.solvedTime = solvedTime;
    }

    public Long getUsedTime() {
        return usedTime;
    }

    public void setUsedTime(Long usedTime) {
        this.usedTime = usedTime;
    }

    public Integer getWrongTimes() {
        return wrongTimes;
    }

    public void setWrongTimes(Integer wrongTimes) {
        this.wrongTimes = wrongTimes;
    }
}
