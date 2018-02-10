package com.eagleoj.web.postman.task;

/**
 * @author Smith
 **/
public class CloseOfficialContestTask implements BaseTask {

    private int cid;

    public CloseOfficialContestTask(int cid) {
        this.cid = cid;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }
}
