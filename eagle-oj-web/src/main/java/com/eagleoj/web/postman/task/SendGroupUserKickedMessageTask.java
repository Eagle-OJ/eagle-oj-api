package com.eagleoj.web.postman.task;

/**
 * @author Smith
 **/
public class SendGroupUserKickedMessageTask implements BaseTask {
    private String groupName;

    private int gid;

    private int uid;

    public SendGroupUserKickedMessageTask(String groupName, int gid, int uid) {
        this.groupName = groupName;
        this.gid = gid;
        this.uid = uid;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public int getGid() {
        return gid;
    }

    public void setGid(int gid) {
        this.gid = gid;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }
}
