package com.eagleoj.web.service;

import com.eagleoj.web.entity.GroupEntity;
import java.util.List;

/**
 * @author Smith
 **/
public interface GroupService {

    int save(int owner, String name, String password);

    GroupEntity getGroup(int gid);

    List<GroupEntity> listUserGroups(int owner);

    boolean updateGroupByGid(int gid, String name, String password);
}
