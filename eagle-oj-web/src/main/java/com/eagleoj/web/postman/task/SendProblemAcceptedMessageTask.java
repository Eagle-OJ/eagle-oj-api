package com.eagleoj.web.postman.task;

/**
 * @author Smith
 **/
public class SendProblemAcceptedMessageTask implements BaseTask {
    private String title;

    private int pid;

    private int uid;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }
}
