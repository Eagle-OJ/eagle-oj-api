package org.inlighting.oj.web.security;

import java.util.Set;

/**
 * @author Smith
 **/
public class UserSession {

    private int uid;

    private int role;

    private Set<String> permission;

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public Set<String> getPermission() {
        return permission;
    }

    public void setPermission(Set<String> permission) {
        this.permission = permission;
    }
}
