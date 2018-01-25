package com.eagleoj.judge.entity;

/**
 * @author Smith
 **/
public class TestCaseRequestEntity {
    private String stdin;

    private String stdout;

    public TestCaseRequestEntity(String stdin, String stdout) {
        this.stdin = stdin;
        this.stdout = stdout;
    }

    public TestCaseRequestEntity() {
    }

    public String getStdin() {
        return stdin;
    }

    public void setStdin(String stdin) {
        this.stdin = stdin;
    }

    public String getStdout() {
        return stdout;
    }

    public void setStdout(String stdout) {
        this.stdout = stdout;
    }
}
