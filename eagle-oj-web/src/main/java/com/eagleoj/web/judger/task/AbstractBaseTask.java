package com.eagleoj.web.judger.task;

import com.eagleoj.judge.LanguageEnum;
import com.eagleoj.web.entity.TestCaseEntity;

import java.util.List;

/**
 * @author Smith
 **/
public abstract class AbstractBaseTask implements JudgeTask {

    private String id;

    private String sourceCode;

    private LanguageEnum lang;

    private List<TestCaseEntity> testCases;

    private int time;

    private int memory;

    private int priority;

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getSourceCode() {
        return sourceCode;
    }

    public void setSourceCode(String sourceCode) {
        this.sourceCode = sourceCode;
    }

    @Override
    public LanguageEnum getLang() {
        return lang;
    }

    public void setLang(LanguageEnum lang) {
        this.lang = lang;
    }

    @Override
    public List<TestCaseEntity> getTestCases() {
        return testCases;
    }

    public void setTestCases(List<TestCaseEntity> testCases) {
        this.testCases = testCases;
    }

    @Override
    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    @Override
    public int getMemory() {
        return memory;
    }

    public void setMemory(int memory) {
        this.memory = memory;
    }

    @Override
    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }
}
