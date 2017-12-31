package org.inlighting.oj.web.entity;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author Smith
 **/
public class ProblemEntity {
    private int pid;

    private int owner;

    private String title;

    @JSONField(name = "lang")
    private JSONArray lang;

    private JSONObject description;

    private int difficult;

    @JSONField(name = "input_format")
    private JSONObject inputFormat;

    @JSONField(name = "output_format")
    private JSONObject outputFormat;

    private JSONArray samples;

    private int time;

    private int memory;

    @JSONField(name = "submit_times")
    private int submitTimes;

    @JSONField(name = "used_times")
    private int usedTimes;

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

    private int status;

    @JSONField(name = "create_time")
    private long createTime;

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public int getOwner() {
        return owner;
    }

    public void setOwner(int owner) {
        this.owner = owner;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public JSONArray getLang() {
        return lang;
    }

    public void setLang(JSONArray lang) {
        this.lang = lang;
    }

    public JSONObject getDescription() {
        return description;
    }

    public void setDescription(JSONObject description) {
        this.description = description;
    }

    public int getDifficult() {
        return difficult;
    }

    public void setDifficult(int difficult) {
        this.difficult = difficult;
    }

    public JSONObject getInputFormat() {
        return inputFormat;
    }

    public void setInputFormat(JSONObject inputFormat) {
        this.inputFormat = inputFormat;
    }

    public JSONObject getOutputFormat() {
        return outputFormat;
    }

    public void setOutputFormat(JSONObject outputFormat) {
        this.outputFormat = outputFormat;
    }

    public JSONArray getSamples() {
        return samples;
    }

    public void setSamples(JSONArray samples) {
        this.samples = samples;
    }

    public int getSubmitTimes() {
        return submitTimes;
    }

    public void setSubmitTimes(int submitTimes) {
        this.submitTimes = submitTimes;
    }

    public int getUsedTimes() {
        return usedTimes;
    }

    public void setUsedTimes(int usedTimes) {
        this.usedTimes = usedTimes;
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getMemory() {
        return memory;
    }

    public void setMemory(int memory) {
        this.memory = memory;
    }
}
