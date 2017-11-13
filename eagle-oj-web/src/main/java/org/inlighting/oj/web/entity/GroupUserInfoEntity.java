package org.inlighting.oj.web.entity;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author Smith
 **/
public class GroupUserInfoEntity {

    private int gid;

    private int uid;

    private int score;

    @JSONField(name = "submit_times")
    private int submitTimes;

    @JSONField(name = "accept_times")
    private int acceptTimes;

    @JSONField(name = "join_time")
    private long joinTime;

    public int getGid() {
        return gid;
    }

    public void setGid(int gid) {
        this.gid = gid;
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

}
