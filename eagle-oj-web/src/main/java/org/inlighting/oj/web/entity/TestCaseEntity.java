package org.inlighting.oj.web.entity;

/**
 * @author Smith
 **/
public class TestCaseEntity {

    private int tid;

    private int pid;

    private String stdin;

    private String stdout;

    private int strength;

    private long createTime;


    public TestCaseEntity() {
    }

    public TestCaseEntity(int tid, String stdin, String stdout, int strength) {
        this.tid = tid;
        this.stdin = stdin;
        this.stdout = stdout;
        this.strength = strength;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
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

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public int getTid() {
        return tid;
    }

    public void setTid(int tid) {
        this.tid = tid;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }
}
