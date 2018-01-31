package com.eagleoj.web.service.impl;

import com.eagleoj.web.controller.exception.WebErrorException;
import com.eagleoj.web.dao.GroupUserMapper;
import com.eagleoj.web.entity.GroupEntity;
import com.eagleoj.web.entity.GroupUserEntity;
import com.eagleoj.web.entity.UserEntity;
import com.eagleoj.web.security.SessionHelper;
import com.eagleoj.web.service.GroupService;
import com.eagleoj.web.service.GroupUserService;
import com.eagleoj.web.service.UserService;
import com.eagleoj.web.util.WebUtil;
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

    @Autowired
    private GroupService groupService;

    @Autowired
    private UserService userService;

    @Override
    public List<Map<String, Object>> listUserJoinedGroups(int uid) {
        return groupUserMapper.listUserJoinedGroups(uid);
    }

    @Override
    public GroupUserEntity getGroupMember(int gid, int uid) {
        GroupUserEntity groupUserEntity = groupUserMapper.getByGidUid(gid, uid);
        WebUtil.assertNotNull(groupUserEntity, "你不在小组中");
        return groupUserEntity;
    }

    @Override
    public boolean isUserInGroup(int gid, int uid) {
        return groupUserMapper.getByGidUid(gid, uid) != null;
    }

    @Override
    public void updateGroupName(int gid, int uid, String groupName) {
        GroupUserEntity entity = new GroupUserEntity();
        entity.setGroupName(groupName);
        boolean flag = groupUserMapper.updateByGidUid(gid, uid, entity) == 1;
        WebUtil.assertIsSuccess(flag, "组内成员名称更换失败");
    }

    public void updateGroup(int gid, int uid, GroupUserEntity entity) {
        boolean flag = groupUserMapper.updateByGidUid(gid, uid, entity) == 1;
        WebUtil.assertIsSuccess(flag, "组内成员信息更新失败");
    }

    @Override
    public Map<String, Object> getGroupUserInfo(int gid, int uid) {
        Map<String, Object> data = groupUserMapper.getUserInfoByGidUid(gid, uid);
        WebUtil.assertNotNull(data, "你不在小组中");
        return data;
    }

    @Override
    public List<GroupUserEntity> listGroupMembers(int gid) {
        return groupUserMapper.listGroupMembersByGid(gid);
    }

    @Override
    public void deleteGroupMember(int gid, int uid) {
        boolean flag = groupUserMapper.deleteGroupMemberByGidUid(gid, uid) == 1;
        WebUtil.assertIsSuccess(flag, "删除组内用户失败");
    }

    @Override
    public void joinGroup(int gid, int uid, String password) {
        GroupEntity groupEntity = groupService.getGroup(gid);
        // 密码校对
        if (groupEntity.getPassword() != null) {
            if (! password.equals(groupEntity.getPassword())) {
                throw new WebErrorException("密码错误");
            }
        }

        // 查看是否已经在小组里面
        if (isUserInGroup(gid, uid)) {
            throw new WebErrorException("你已经在小组中");
        }

        // 获取用户nickname充当group_name
        UserEntity userEntity = userService.getUserByUid(uid);

        GroupUserEntity entity = new GroupUserEntity();
        entity.setGid(gid);
        entity.setUid(uid);
        entity.setGroupName(userEntity.getNickname());
        entity.setJoinTime(System.currentTimeMillis());
        boolean flag =  groupUserMapper.save(entity) == 1;
        WebUtil.assertIsSuccess(flag, "加入小组失败");
    }
}
