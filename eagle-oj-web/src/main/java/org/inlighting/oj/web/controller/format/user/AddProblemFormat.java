package org.inlighting.oj.web.controller.format.user;

import com.alibaba.fastjson.JSONArray;
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

    @JSONField(name = "code_language")
    @NotNull
    private JSONArray codeLanguage;

    @Length(max = 100)
    @NotNull
    private String description;

    @Range(min = 1, max = 4)
    @NotNull
    private int difficult;

    @JSONField(name = "input_format")
    @Length(max = 200)
    @NotNull
    private String inputFormat;

    @JSONField(name = "output_format")
    @Length(max = 200)
    @NotNull
    private String outputFormat;

    @Length(max = 100)
    @NotNull
    private String constraint;

    private JSONArray sample;

    private JSONArray tag;

    @Range(min = 1, max = 3)
    private int share;

    public JSONArray getCodeLanguage() {
        return codeLanguage;
    }

    public void setCodeLanguage(JSONArray codeLanguage) {
        this.codeLanguage = codeLanguage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDifficult() {
        return difficult;
    }

    public void setDifficult(int difficult) {
        this.difficult = difficult;
    }

    public String getInputFormat() {
        return inputFormat;
    }

    public void setInputFormat(String inputFormat) {
        this.inputFormat = inputFormat;
    }

    public String getOutputFormat() {
        return outputFormat;
    }

    public void setOutputFormat(String outputFormat) {
        this.outputFormat = outputFormat;
    }

    public String getConstraint() {
        return constraint;
    }

    public void setConstraint(String constraint) {
        this.constraint = constraint;
    }

    public JSONArray getSample() {
        return sample;
    }

    public void setSample(JSONArray sample) {
        this.sample = sample;
    }

    public JSONArray getTag() {
        return tag;
    }

    public void setTag(JSONArray tag) {
        this.tag = tag;
    }

    public int getShare() {
        return share;
    }

    public void setShare(int share) {
        this.share = share;
    }
}
