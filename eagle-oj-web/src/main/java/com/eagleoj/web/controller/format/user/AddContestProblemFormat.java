package com.eagleoj.web.controller.format.user;

import com.alibaba.fastjson.JSONArray;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;

/**
 * @author Smith
 **/
public class AddContestProblemFormat {

    @NotNull
    @Range(min = 1)
    private Integer pid;

    @NotNull
    @Range(min = 1)
    private Integer displayId;

    @NotNull
    @Range(min = 1)
    private Integer score;

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Integer getDisplayId() {
        return displayId;
    }

    public void setDisplayId(Integer displayId) {
        this.displayId = displayId;
    }
}
