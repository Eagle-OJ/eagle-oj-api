package com.eagleoj.web.entity;

import com.eagleoj.judge.ResultEnum;

/**
 * @author Smith
 **/
public class ContestProblemUserEntity {
    private Integer cid;

    private Integer pid;

    private Integer uid;

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
}
