package com.eagleoj.web.postman.task;

/**
 * @author Smith
 **/
public class SendProblemAuditingMessageTask implements BaseTask {
    private String title;

    private int pid;

    private int uid;

    private boolean isAccepted;

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

    public boolean isAccepted() {
        return isAccepted;
    }

    public void setAccepted(boolean accepted) {
        isAccepted = accepted;
    }
}
