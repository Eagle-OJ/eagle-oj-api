package org.inlighting.oj.web.judger;

import org.inlighting.oj.judge.LanguageEnum;
import org.inlighting.oj.judge.entity.TestCaseRequestEntity;
import org.inlighting.oj.web.entity.ContestEntity;
import org.inlighting.oj.web.entity.ContestProblemEntity;
import org.inlighting.oj.web.entity.ProblemEntity;
import org.inlighting.oj.web.entity.TestCaseEntity;

import java.util.List;

/**
 * @author Smith
 **/
public class JudgerTask {
    private String id;

    private int priority;

    private int problemId;

    private int contestId;

    private int owner;

    private LanguageEnum lang;

    private String sourceCode;

    private int time;

    private int memory;

    private boolean testMode;

    private List<TestCaseRequestEntity> testCases;

    private List<TestCaseEntity> addTestCaseEntities;

    private ProblemEntity addProblemEntity;

    private ContestEntity addContestEntity;

    private ContestProblemEntity addContestProblemEntity;

    private long refreshTime;

    public int getContestId() {
        return contestId;
    }

    public void setContestId(int contestId) {
        this.contestId = contestId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getProblemId() {
        return problemId;
    }

    public void setProblemId(int problemId) {
        this.problemId = problemId;
    }

    public LanguageEnum getLang() {
        return lang;
    }

    public void setLang(LanguageEnum lang) {
        this.lang = lang;
    }

    public String getSourceCode() {
        return sourceCode;
    }

    public void setSourceCode(String sourceCode) {
        this.sourceCode = sourceCode;
    }

    public boolean isTestMode() {
        return testMode;
    }

    public void setTestMode(boolean testMode) {
        this.testMode = testMode;
    }

    public List<TestCaseRequestEntity> getTestCases() {
        return testCases;
    }

    public void setTestCases(List<TestCaseRequestEntity> testCases) {
        this.testCases = testCases;
    }

    public long getRefreshTime() {
        return refreshTime;
    }

    public void setRefreshTime(long refreshTime) {
        this.refreshTime = refreshTime;
    }

    public int getOwner() {
        return owner;
    }

    public void setOwner(int owner) {
        this.owner = owner;
    }

    public List<TestCaseEntity> getAddTestCaseEntities() {
        return addTestCaseEntities;
    }

    public void setAddTestCaseEntities(List<TestCaseEntity> addTestCaseEntities) {
        this.addTestCaseEntities = addTestCaseEntities;
    }

    public ProblemEntity getAddProblemEntity() {
        return addProblemEntity;
    }

    public void setAddProblemEntity(ProblemEntity addProblemEntity) {
        this.addProblemEntity = addProblemEntity;
    }

    public ContestEntity getAddContestEntity() {
        return addContestEntity;
    }

    public void setAddContestEntity(ContestEntity addContestEntity) {
        this.addContestEntity = addContestEntity;
    }

    public ContestProblemEntity getAddContestProblemEntity() {
        return addContestProblemEntity;
    }

    public void setAddContestProblemEntity(ContestProblemEntity addContestProblemEntity) {
        this.addContestProblemEntity = addContestProblemEntity;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getMemory() {
        return memory;
    }

    public void setMemory(int memory) {
        this.memory = memory;
    }
}
