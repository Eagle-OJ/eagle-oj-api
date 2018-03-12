package com.eagleoj.web.service;

import com.eagleoj.web.entity.GroupUserEntity;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author Smith
 **/
public interface GroupUserService {

    List<Map<String, Object>> listUserJoinedGroups(int uid);

    int countGroupMembers(int gid);

    GroupUserEntity getGroupMember(int gid, int uid);

    boolean isUserInGroup(int gid, int uid);

    Map<String, Object> getGroupUserInfo(int gid, int uid);

    void updateGroupName(int gid, int uid, String groupName);

    void updateGroup(int gid, int uid, GroupUserEntity entity);

    List<GroupUserEntity> listGroupMembers(int gid);

    List<Map<String, Object>> listGroupMembersRank(int gid);

    void deleteGroupMember(int gid, int uid);

    void deleteGroupMembers(int gid);

    void joinGroup(int gid, int uid, String password);
}
