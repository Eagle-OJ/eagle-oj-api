package org.inlighting.oj.web.controller.format.user;

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
public class AddProblemFormat {

    @NotBlank
    @Length(max = 100)
    @NotNull
    private String title;

    @NotNull
    private JSONObject description;

    @JSONField(name = "input_format")
    @NotNull
    private JSONObject inputFormat;

    @JSONField(name = "output_format")
    @NotNull
    private JSONObject outputFormat;

    @Range(min = 0, max = 3)
    @NotNull
    private Integer difficult;

    @NotNull
    private JSONArray samples;

    @NotNull
    private JSONArray tags;

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
}


