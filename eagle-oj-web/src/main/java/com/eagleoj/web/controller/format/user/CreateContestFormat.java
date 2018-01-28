package com.eagleoj.web.controller.format.user;

import com.alibaba.fastjson.annotation.JSONField;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;

/**
 * @author Smith
 **/
public class CreateContestFormat {

    @Range(min = 0)
    private Integer group;

    @NotNull
    @Length(min = 1, max = 50)
    private String name;

    @NotNull
    @Length(min = 1, max = 100)
    private String slogan;

    @NotNull
    @Length(min = 1, max = 500)
    private String description;

    @JSONField(name = "start_time")
    @NotNull
    private Long startTime;

    @JSONField(name = "end_time")
    @NotNull
    private Long endTime;

    @JSONField(name = "total_time")
    private Long totalTime;

    @Length(max = 6)
    private String password;

    @NotNull
    @Range(min = 0, max = 3)
    private Integer type;

    @Range(min = 0, max = 1)
    private Integer status;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSlogan() {
        return slogan;
    }

    public void setSlogan(String slogan) {
        this.slogan = slogan;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    public Long getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(Long totalTime) {
        this.totalTime = totalTime;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getType() {
        return type;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getGroup() {
        return group;
    }

    public void setGroup(Integer group) {
        this.group = group;
    }
}
