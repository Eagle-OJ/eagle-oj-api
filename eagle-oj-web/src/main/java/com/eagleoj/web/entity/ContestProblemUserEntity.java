package com.eagleoj.web.entity;

import com.eagleoj.judge.ResultEnum;

/**
 * @author Smith
 **/
public class ContestProblemUserEntity {
    private int cid;

    private int pid;

    private int uid;

    private int score;

    private ResultEnum status;

    private long solvedTime;

    private long usedTime;

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public ResultEnum getStatus() {
        return status;
    }

    public void setStatus(ResultEnum status) {
        this.status = status;
    }

    public long getSolvedTime() {
        return solvedTime;
    }

    public void setSolvedTime(long solvedTime) {
        this.solvedTime = solvedTime;
    }

    public long getUsedTime() {
        return usedTime;
    }

    public void setUsedTime(long usedTime) {
        this.usedTime = usedTime;
    }
}
