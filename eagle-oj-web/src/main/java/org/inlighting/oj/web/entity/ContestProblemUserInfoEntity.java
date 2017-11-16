package org.inlighting.oj.web.entity;

import com.alibaba.fastjson.annotation.JSONField;
import org.inlighting.oj.judge.config.ProblemStatusEnum;

/**
 * @author Smith
 **/
public class ContestProblemUserInfoEntity {
    private int cid;

    private int pid;

    private int uid;

    private int score;

    private ProblemStatusEnum status;

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

    public ProblemStatusEnum getStatus() {
        return status;
    }

    public void setStatus(ProblemStatusEnum status) {
        this.status = status;
    }
}
