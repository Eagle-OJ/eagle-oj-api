package com.eagleoj.web.postman.task;

/**
 * @author Smith
 **/
public class CloseNormalContestTask implements BaseTask {
    private int cid;

    public CloseNormalContestTask(int cid) {
        this.cid = cid;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }
}
