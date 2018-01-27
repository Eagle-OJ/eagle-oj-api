package com.eagleoj.web.service;

import com.eagleoj.web.entity.GroupUserEntity;

import java.util.List;
import java.util.Map;

/**
 * @author Smith
 **/
public interface GroupUserService {
    boolean save(int gid, int uid);

    List<Map<String, Object>> listUserJoinedGroups(int uid);

    GroupUserEntity getGroupMember(int gid, int uid);

    boolean updateRealNameByGidUid(int gid, int uid, String realName);

    boolean isUserInGroup(int gid, int uid);

    List<Map<String, Object>> listGroupMembers(int gid);

    boolean deleteGroupMember(int gid, int uid);
}
