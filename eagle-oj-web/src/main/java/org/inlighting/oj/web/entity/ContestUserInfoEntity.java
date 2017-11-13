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

    @JSONField(name = "used_time")
    private long usedTime;

    @JSONField(name = "join_time")
    private long joinTime;

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

    public long getJoinTime() {
        return joinTime;
    }

    public void setJoinTime(long joinTime) {
        this.joinTime = joinTime;
    }

    public long getUsedTime() {
        return usedTime;
    }

    public void setUsedTime(long usedTime) {
        this.usedTime = usedTime;
    }
}
