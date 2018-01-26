package com.eagleoj.web.service.impl;

import com.eagleoj.web.dao.GroupUserMapper;
import com.eagleoj.web.entity.GroupUserEntity;
import com.eagleoj.web.service.GroupUserService;
import com.github.pagehelper.PageRowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author Smith
 **/
@Service
public class GroupUserServiceImpl implements GroupUserService {

    @Autowired
    private GroupUserMapper groupUserMapper;

    @Override
    public boolean save(int gid, int uid) {
        GroupUserEntity entity = new GroupUserEntity();
        entity.setGid(gid);
        entity.setUid(uid);
        entity.setJoinTime(System.currentTimeMillis());
        return groupUserMapper.save(entity) == 1;
    }

    @Override
    public List<Map<String, Object>> listUserJoinedGroups(int uid) {
        return groupUserMapper.listUserJoinedGroups(uid);
    }

    @Override
    public GroupUserEntity getGroupMember(int gid, int uid) {
        // 根据Uid和Gid获取group
        return groupUserMapper.getByGidUid(gid, uid);
    }

    @Override
    public boolean updateRealNameByGidUid(int gid, int uid, String realName) {
        GroupUserEntity entity = new GroupUserEntity();
        entity.setRealName(realName);
        return groupUserMapper.updateByGidUid(gid, uid, entity) == 1;
    }

    @Override
    public boolean isUserInGroup(int gid, int uid) {
        return getGroupMember(gid, uid) != null;
    }

    @Override
    public List<Map<String, Object>> listGroupMembers(int gid) {
        return groupUserMapper.listGroupMembersByGid(gid);
    }

    @Override
    public boolean deleteGroupMember(int gid, int uid) {
        //删除
        return groupUserMapper.deleteGroupMemberByGidUid(gid, uid) == 1;
    }
}
