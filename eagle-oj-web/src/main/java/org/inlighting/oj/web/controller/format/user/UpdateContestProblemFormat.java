package org.inlighting.oj.web.controller.format.user;

import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;

/**
 * @author Smith
 **/
public class UpdateContestProblemFormat {

    @NotNull
    @Range(min = 1)
    private Integer score;

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
