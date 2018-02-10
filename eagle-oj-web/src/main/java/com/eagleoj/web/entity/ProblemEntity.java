package com.eagleoj.web.entity;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author Smith
 **/
public class ProblemEntity {
    private Integer pid;

    private Integer owner;

    private String title;

    @JSONField(name = "lang")
    private JSONArray lang;

    private JSONObject description;

    private Integer difficult;

    @JSONField(name = "input_format")
    private JSONObject inputFormat;

    @JSONField(name = "output_format")
    private JSONObject outputFormat;

    private JSONArray samples;

    private Integer time;

    private Integer memory;

    @JSONField(name = "submit_times")
    private Integer submitTimes;

    @JSONField(name = "used_times")
    private Integer usedTimes;

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

    private Integer status;

    @JSONField(name = "create_time")
    private Long createTime;

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public Integer getOwner() {
        return owner;
    }

    public void setOwner(Integer owner) {
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

    public Integer getDifficult() {
        return difficult;
    }

    public void setDifficult(Integer difficult) {
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

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    public Integer getMemory() {
        return memory;
    }

    public void setMemory(Integer memory) {
        this.memory = memory;
    }

    public Integer getSubmitTimes() {
        return submitTimes;
    }

    public void setSubmitTimes(Integer submitTimes) {
        this.submitTimes = submitTimes;
    }

    public Integer getUsedTimes() {
        return usedTimes;
    }

    public void setUsedTimes(Integer usedTimes) {
        this.usedTimes = usedTimes;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }
}
