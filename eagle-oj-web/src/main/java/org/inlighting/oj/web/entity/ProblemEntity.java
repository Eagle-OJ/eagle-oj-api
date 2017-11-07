package org.inlighting.oj.web.entity;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author Smith
 **/
public class ProblemEntity {
    private int pid;

    private int owner;

    private JSONArray codeLanguage;

    private String title;

    private String description;

    private int difficult;

    @JSONField(name = "input_format")
    private String inputFormat;

    @JSONField(name = "output_format")
    private String outputFormat;

    private String constraint;

    private JSONArray sample;

    private JSONArray moderator;

    private JSONArray tag;

    private int share;

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

    public JSONArray getModerator() {
        return moderator;
    }

    public void setModerator(JSONArray moderator) {
        this.moderator = moderator;
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

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }


}
