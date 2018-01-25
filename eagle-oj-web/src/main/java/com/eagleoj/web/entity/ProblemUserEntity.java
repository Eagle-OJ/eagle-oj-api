package com.eagleoj.web.entity;

import com.eagleoj.judge.ResultEnum;

/**
 * @author Smith
 **/
public class ProblemUserEntity {
    private int pid;

    private int uid;

    private ResultEnum status;

    public ProblemUserEntity(int pid, int uid, ResultEnum status) {
        this.pid = pid;
        this.uid = uid;
        this.status = status;
    }

    public ProblemUserEntity() {
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

    public ResultEnum getStatus() {
        return status;
    }

    public void setStatus(ResultEnum status) {
        this.status = status;
    }
}
