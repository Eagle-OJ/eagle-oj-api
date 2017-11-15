package org.inlighting.oj.web.judger;

import org.inlighting.oj.judge.config.CodeLanguageEnum;
import org.inlighting.oj.web.entity.TestCaseEntity;

import java.util.List;

/**
 * @author Smith
 **/
public class JudgerTaskEntity {
    private String uuid;

    private int priority;

    private int problemId;

    private CodeLanguageEnum codeLanguage;

    private String codeSource;

    private boolean testMode;

    private List<TestCaseEntity> testCaseEntities;

    private long time;

    public JudgerTaskEntity() {
    }

    public JudgerTaskEntity(String uuid, int priority, int problemId, CodeLanguageEnum codeLanguage, String codeSource, boolean testMode, List<TestCaseEntity> testCaseEntities, long time) {
        this.uuid = uuid;
        this.priority = priority;
        this.problemId = problemId;
        this.codeLanguage = codeLanguage;
        this.codeSource = codeSource;
        this.testMode = testMode;
        this.testCaseEntities = testCaseEntities;
        this.time = time;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
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

    public CodeLanguageEnum getCodeLanguage() {
        return codeLanguage;
    }

    public void setCodeLanguage(CodeLanguageEnum codeLanguage) {
        this.codeLanguage = codeLanguage;
    }

    public String getCodeSource() {
        return codeSource;
    }

    public void setCodeSource(String codeSource) {
        this.codeSource = codeSource;
    }

    public boolean isTestMode() {
        return testMode;
    }

    public void setTestMode(boolean testMode) {
        this.testMode = testMode;
    }

    public List<TestCaseEntity> getTestCaseEntities() {
        return testCaseEntities;
    }

    public void setTestCaseEntities(List<TestCaseEntity> testCaseEntities) {
        this.testCaseEntities = testCaseEntities;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
