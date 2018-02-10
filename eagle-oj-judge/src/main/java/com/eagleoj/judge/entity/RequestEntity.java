package com.eagleoj.judge.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.eagleoj.judge.LanguageEnum;

import java.util.List;

/**
 * @author Smith
 **/
public class RequestEntity {
    private LanguageEnum lang;

    @JSONField(name = "source_code")
    private String sourceCode;

    @JSONField(name = "time_limit")
    private int timeLimit;

    @JSONField(name = "memory_limit")
    private int memoryLimit;

    @JSONField(name = "test_cases")
    private List<TestCaseRequestEntity> testCases;

    public RequestEntity(LanguageEnum lang, String sourceCode, int timeLimit, int memoryLimit, List<TestCaseRequestEntity> testCases) {
        this.lang = lang;
        this.sourceCode = sourceCode;
        this.timeLimit = timeLimit;
        this.memoryLimit = memoryLimit;
        this.testCases = testCases;
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

    public int getTimeLimit() {
        return timeLimit;
    }

    public void setTimeLimit(int timeLimit) {
        this.timeLimit = timeLimit;
    }

    public int getMemoryLimit() {
        return memoryLimit;
    }

    public void setMemoryLimit(int memoryLimit) {
        this.memoryLimit = memoryLimit;
    }

    public List<TestCaseRequestEntity> getTestCases() {
        return testCases;
    }

    public void setTestCases(List<TestCaseRequestEntity> testCases) {
        this.testCases = testCases;
    }
}
