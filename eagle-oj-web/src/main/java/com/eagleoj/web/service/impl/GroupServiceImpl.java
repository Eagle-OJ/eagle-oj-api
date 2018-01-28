package com.eagleoj.web.service.impl;

import com.eagleoj.web.dao.GroupMapper;
import com.eagleoj.web.entity.GroupEntity;
import com.eagleoj.web.service.GroupService;
import com.eagleoj.web.util.WebUtil;
import com.github.pagehelper.PageRowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Smith
 **/
@Service
public class GroupServiceImpl implements GroupService {

    @Autowired
    private GroupMapper groupMapper;

    @Override
    public int saveGroup(int owner, String name, String password) {
        GroupEntity groupEntity = new GroupEntity();
        groupEntity.setOwner(owner);
        groupEntity.setPassword(password);
        groupEntity.setName(name);
        groupEntity.setCreateTime(System.currentTimeMillis());
        boolean flag= groupMapper.save(groupEntity) == 1;
        WebUtil.assertIsSuccess(flag, "小组创建失败");
        return groupEntity.getGid();
    }

    @Override
    public GroupEntity getGroup(int gid) {
        GroupEntity groupEntity = groupMapper.getGroupByGid(gid);
        WebUtil.assertNotNull(groupEntity, "小组不存在");
        return groupEntity;
    }

    @Override
    public List<GroupEntity> listUserGroups(int owner) {
        return groupMapper.listGroupsByOwner(owner);
    }

    @Override
    public boolean updateGroupByGid(int gid, String name, String password) {
        GroupEntity entity = new GroupEntity();
        entity.setName(name);
        entity.setPassword(password);
        return groupMapper.updateByGid(gid, entity) == 1;
    }
}
