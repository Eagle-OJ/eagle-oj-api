package org.inlighting.oj.web.entity;

/**
 * @author Smith
 **/
public class TagEntity {

    private int tid;

    private String name;

    private int used;

    public int getTid() {
        return tid;
    }

    public void setTid(int tid) {
        this.tid = tid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getUsed() {
        return used;
    }

    public void setUsed(int used) {
        this.used = used;
    }
}
