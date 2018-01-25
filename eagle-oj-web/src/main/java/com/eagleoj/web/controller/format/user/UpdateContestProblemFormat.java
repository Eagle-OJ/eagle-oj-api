package com.eagleoj.web.controller.format.user;

import com.alibaba.fastjson.annotation.JSONField;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;

/**
 * @author Smith
 **/
public class UpdateContestProblemFormat {

    @JSONField(name = "display_id")
    @NotNull
    @Range(min = 1)
    private Integer displayId;

    @NotNull
    @Range(min = 1)
    private Integer score;

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Integer getDisplayId() {
        return displayId;
    }

    public void setDisplayId(Integer displayId) {
        this.displayId = displayId;
    }

    public void setScore(Integer score) {
        this.score = score;
    }
}
