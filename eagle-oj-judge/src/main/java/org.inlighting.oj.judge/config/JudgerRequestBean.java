package org.inlighting.oj.judge.config;

import org.inlighting.oj.judge.config.CodeLanguageEnum;

public class JudgerRequestBean {

    private int testCaseNumber;

    private CodeLanguageEnum codeLanguage;

    private String sourceCode;

    private String[] stdin = null;

    private String[] expectResult = null;

    private int timeLimit = 3;

    private int memoryLimit = 128;

    public int getTestCaseNumber() {
        return testCaseNumber;
    }

    public void setTestCaseNumber(int testCaseNumber) {
        this.testCaseNumber = testCaseNumber;
    }

    public CodeLanguageEnum getCodeLanguage() {
        return codeLanguage;
    }

    public void setCodeLanguage(CodeLanguageEnum codeLanguage) {
        this.codeLanguage = codeLanguage;
    }

    public String getSourceCode() {
        return sourceCode;
    }

    public void setSourceCode(String sourceCode) {
        this.sourceCode = sourceCode;
    }

    public String[] getStdin() {
        return stdin;
    }

    public void setStdin(String[] stdin) {
        this.stdin = stdin;
    }

    public String[] getExpectResult() {
        return expectResult;
    }

    public void setExpectResult(String[] expectResult) {
        this.expectResult = expectResult;
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
}
