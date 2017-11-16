package org.inlighting.oj.web.judger;

import com.alibaba.fastjson.annotation.JSONField;
import org.inlighting.oj.judge.config.JudgeResponseBean;

/**
 * @author Smith
 **/
public class JudgerTaskResultEntity {

    @JSONField(serialize = false)
    private JudgerTaskEntity judgerTaskEntity;

    @JSONField(name = "response")
    private JudgeResponseBean judgeResponseBean;

    private int owner;

    @JSONField(serialize = false)
    private int contestId;

    @JSONField(serialize = false)
    private int contestType;

    @JSONField(name = "total_score")
    private int totalScore;

    private int score;

    @JSONField(name = "status")
    private JudgerTaskStatus status;

    public JudgerTaskResultEntity() {
    }

    public JudgerTaskResultEntity(JudgerTaskEntity judgerTaskEntity, int owner, int contestId, int contestType, int totalScore, JudgerTaskStatus status) {
        this.judgerTaskEntity = judgerTaskEntity;
        this.owner = owner;
        this.contestId = contestId;
        this.contestType = contestType;
        this.totalScore = totalScore;
        this.status = status;
    }

    public JudgerTaskEntity getJudgerTaskEntity() {
        return judgerTaskEntity;
    }

    public void setJudgerTaskEntity(JudgerTaskEntity judgerTaskEntity) {
        this.judgerTaskEntity = judgerTaskEntity;
    }

    public int getOwner() {
        return owner;
    }

    public void setOwner(int owner) {
        this.owner = owner;
    }

    public int getContestId() {
        return contestId;
    }

    public void setContestId(int contestId) {
        this.contestId = contestId;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public JudgerTaskStatus getStatus() {
        return status;
    }

    public void setStatus(JudgerTaskStatus status) {
        this.status = status;
    }

    public JudgeResponseBean getJudgeResponseBean() {
        return judgeResponseBean;
    }

    public void setJudgeResponseBean(JudgeResponseBean judgeResponseBean) {
        this.judgeResponseBean = judgeResponseBean;
    }

    public int getContestType() {
        return contestType;
    }

    public void setContestType(int contestType) {
        this.contestType = contestType;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }
}
