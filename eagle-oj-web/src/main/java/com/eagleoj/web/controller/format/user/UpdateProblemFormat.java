package com.eagleoj.web.controller.format.user;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;

/**
 * @author Smith
 **/
public class UpdateProblemFormat {
    @Length(max = 100)
    private String title;

    private JSONObject description;

    @JSONField(name = "input_format")
    private JSONObject inputFormat;

    @JSONField(name = "output_format")
    private JSONObject outputFormat;

    @Range(min = 0, max = 3)
    private Integer difficult;

    private JSONArray samples;

    private JSONArray tags;

    private JSONArray lang;

    @Range(min = 3, max = 10)
    private Integer time;

    @Range(min = 1, max = 256)
    private Integer memory;

    private Boolean isShared;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public JSONObject getDescription() {
        return description;
    }

    public void setDescription(JSONObject description) {
        this.description = description;
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

    public Integer getDifficult() {
        return difficult;
    }

    public void setDifficult(Integer difficult) {
        this.difficult = difficult;
    }

    public JSONArray getSamples() {
        return samples;
    }

    public void setSamples(JSONArray samples) {
        this.samples = samples;
    }

    public JSONArray getTags() {
        return tags;
    }

    public void setTags(JSONArray tags) {
        this.tags = tags;
    }

    public JSONArray getLang() {
        return lang;
    }

    public void setLang(JSONArray lang) {
        this.lang = lang;
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

    public Boolean getShared() {
        return isShared;
    }

    public void setShared(Boolean shared) {
        isShared = shared;
    }
}
