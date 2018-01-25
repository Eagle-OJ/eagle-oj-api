package com.eagleoj.web.entity;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

/**
 * @author Smith
 **/
public class UserLogEntity {

    @JSONField(name = "date", format = "MM-dd")
    private Date date;

    private Integer uid;

    @JSONField(name = "submit_times")
    private Integer submitTimes;

    @JSONField(name = "ac_times")
    private Integer ACTimes;

    @JSONField(name = "wa_times")
    private Integer WATimes;

    @JSONField(name = "rte_times")
    private Integer RTETimes;

    @JSONField(name = "tle_times")
    private Integer TLETimes;

    @JSONField(name = "ce_times")
    private Integer CETimes;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Integer getSubmitTimes() {
        return submitTimes;
    }

    public void setSubmitTimes(Integer submitTimes) {
        this.submitTimes = submitTimes;
    }

    public Integer getACTimes() {
        return ACTimes;
    }

    public void setACTimes(Integer ACTimes) {
        this.ACTimes = ACTimes;
    }

    public Integer getWATimes() {
        return WATimes;
    }

    public void setWATimes(Integer WATimes) {
        this.WATimes = WATimes;
    }

    public Integer getRTETimes() {
        return RTETimes;
    }

    public void setRTETimes(Integer RTETimes) {
        this.RTETimes = RTETimes;
    }

    public Integer getTLETimes() {
        return TLETimes;
    }

    public void setTLETimes(Integer TLETimes) {
        this.TLETimes = TLETimes;
    }

    public Integer getCETimes() {
        return CETimes;
    }

    public void setCETimes(Integer CETimes) {
        this.CETimes = CETimes;
    }
}
