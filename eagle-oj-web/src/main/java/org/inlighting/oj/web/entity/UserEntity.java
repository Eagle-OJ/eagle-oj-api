package org.inlighting.oj.web.entity;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author Smith
 **/
public class UserEntity {
    private int uid;

    private String email;

    private String nickname;

    @JSONField(name = "real_name")
    private String realName;

    private int avatar;

    private String password;

    private int role;

    private JSONArray permission;

    @JSONField(name = "submit_times")
    private int submitTimes;

    @JSONField(name = "contest_times")
    private int contestTimes;

    @JSONField(name = "ac_times")
    private int ACTimes;

    @JSONField(name = "wa_times")
    private int WATimes;

    @JSONField(name = "rte_times")
    private int RTETimes;

    @JSONField(name = "tle_times")
    private int TLETimes;

    @JSONField(name = "ce_times")
    private int CETimes;

    @JSONField(name = "finished_problems")
    private int finishedProblems;

    private int score;

    private int gender;

    private String motto;

    @JSONField(name = "register_time")
    private long registerTime;

    private int verified;

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
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

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public int getAvatar() {
        return avatar;
    }

    public void setAvatar(int avatar) {
        this.avatar = avatar;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public JSONArray getPermission() {
        return permission;
    }

    public void setPermission(JSONArray permission) {
        this.permission = permission;
    }

    public int getSubmitTimes() {
        return submitTimes;
    }

    public void setSubmitTimes(int submitTimes) {
        this.submitTimes = submitTimes;
    }

    public int getContestTimes() {
        return contestTimes;
    }

    public void setContestTimes(int contestTimes) {
        this.contestTimes = contestTimes;
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

    public int getFinishedProblems() {
        return finishedProblems;
    }

    public void setFinishedProblems(int finishedProblems) {
        this.finishedProblems = finishedProblems;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getMotto() {
        return motto;
    }

    public void setMotto(String motto) {
        this.motto = motto;
    }

    public long getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(long registerTime) {
        this.registerTime = registerTime;
    }

    public int getVerified() {
        return verified;
    }

    public void setVerified(int verified) {
        this.verified = verified;
    }
}
