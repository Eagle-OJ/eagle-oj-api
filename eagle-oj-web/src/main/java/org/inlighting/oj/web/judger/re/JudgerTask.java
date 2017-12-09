package org.inlighting.oj.web.judger.re;

import org.inlighting.oj.judge.LanguageEnum;
import org.inlighting.oj.judge.entity.TestCaseRequestEntity;

import java.util.List;

/**
 * @author Smith
 **/
public class JudgerTask {
    private String id;

    private int priority;

    private int problemId;

    private int contestId;

    private LanguageEnum lang;

    private String sourceCode;

    private boolean testMode;

    private List<TestCaseRequestEntity> testCases;

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
}
