package org.inlighting.oj.web.entity;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author Smith
 **/
public class ContestUserInfoEntity {
    private int cid;

    private int uid;

    private int score;

    @JSONField(name = "submit_times")
    private int submitTimes;

    @JSONField(name = "accept_times")
    private int acceptTimes;

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
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

    public int getSubmitTimes() {
        return submitTimes;
    }

    public void setSubmitTimes(int submitTimes) {
        this.submitTimes = submitTimes;
    }

    public int getAcceptTimes() {
        return acceptTimes;
    }

    public void setAcceptTimes(int acceptTimes) {
        this.acceptTimes = acceptTimes;
    }
}
