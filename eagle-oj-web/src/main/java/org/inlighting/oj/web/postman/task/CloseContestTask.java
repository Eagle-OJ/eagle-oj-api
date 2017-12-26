package org.inlighting.oj.web.postman.task;

/**
 * @author Smith
 **/
public class CloseContestTask extends BaseTask {
    private int cid;

    public CloseContestTask(int cid, int type) {
        this.cid = cid;
        this.type = type;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }
}
