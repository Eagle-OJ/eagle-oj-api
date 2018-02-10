package com.eagleoj.web.entity;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author Smith
 **/
public class UserEntity {
    private Integer uid;

    private String email;

    private String nickname;

    private Integer avatar;

    private String password;

    private Integer role;

    private JSONArray permission;

    @JSONField(name = "submit_times")
    private Integer submitTimes;

    @JSONField(name = "contest_times")
    private Integer contestTimes;

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

    @JSONField(name = "finished_problems")
    private Integer finishedProblems;

    private Integer gender;

    private String motto;

    @JSONField(name = "register_time")
    private Long registerTime;

    private Integer verified;

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Integer getAvatar() {
        return avatar;
    }

    public void setAvatar(Integer avatar) {
        this.avatar = avatar;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    public JSONArray getPermission() {
        return permission;
    }

    public void setPermission(JSONArray permission) {
        this.permission = permission;
    }

    public Integer getSubmitTimes() {
        return submitTimes;
    }

    public void setSubmitTimes(Integer submitTimes) {
        this.submitTimes = submitTimes;
    }

    public Integer getContestTimes() {
        return contestTimes;
    }

    public void setContestTimes(Integer contestTimes) {
        this.contestTimes = contestTimes;
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

    public Integer getFinishedProblems() {
        return finishedProblems;
    }

    public void setFinishedProblems(Integer finishedProblems) {
        this.finishedProblems = finishedProblems;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getMotto() {
        return motto;
    }

    public void setMotto(String motto) {
        this.motto = motto;
    }

    public Long getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(Long registerTime) {
        this.registerTime = registerTime;
    }

    public Integer getVerified() {
        return verified;
    }

    public void setVerified(Integer verified) {
        this.verified = verified;
    }
}
