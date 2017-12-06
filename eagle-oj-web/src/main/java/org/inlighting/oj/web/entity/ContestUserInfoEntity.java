package org.inlighting.oj.web.entity;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author Smith
 **/
public class ContestUserInfoEntity {
    private int cid;

    private int uid;

    @JSONField(name = "submit_times")
    private int submitTimes;

    @JSONField(name = "accept_times")
    private int ACTimes;

    @JSONField(name = "wa_times")
    private int WATimes;

    @JSONField(name = "rte_times")
    private int RTETimes;

    @JSONField(name = "tle_times")
    private int TLETimes;

    @JSONField(name = "ce_times")
    private int CETimes;

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

    public int getSubmitTimes() {
        return submitTimes;
    }

    public void setSubmitTimes(int submitTimes) {
        this.submitTimes = submitTimes;
    }

    public int getACTimes() {
        return ACTimes;
    }

    public void setACTimes(int ACTimes) {
        this.ACTimes = ACTimes;
    }

    public int getWATimes() {
        return WATimes;
    }

    public void setWATimes(int WATimes) {
        this.WATimes = WATimes;
    }

    public int getRTETimes() {
        return RTETimes;
    }

    public void setRTETimes(int RTETimes) {
        this.RTETimes = RTETimes;
    }

    public int getTLETimes() {
        return TLETimes;
    }

    public void setTLETimes(int TLETimes) {
        this.TLETimes = TLETimes;
    }

    public int getCETimes() {
        return CETimes;
    }

    public void setCETimes(int CETimes) {
        this.CETimes = CETimes;
    }

    public long getJoinTime() {
        return joinTime;
    }

    public void setJoinTime(long joinTime) {
        this.joinTime = joinTime;
    }
}
