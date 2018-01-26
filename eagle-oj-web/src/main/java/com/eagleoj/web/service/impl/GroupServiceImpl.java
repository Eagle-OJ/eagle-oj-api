package com.eagleoj.web.service.impl;

import com.eagleoj.web.dao.GroupMapper;
import com.eagleoj.web.entity.GroupEntity;
import com.eagleoj.web.service.GroupService;
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
    public int save(int owner, String name, String password) {
        GroupEntity groupEntity = new GroupEntity();
        groupEntity.setOwner(owner);
        groupEntity.setPassword(password);
        groupEntity.setName(name);
        groupEntity.setCreateTime(System.currentTimeMillis());
        boolean flag= groupMapper.save(groupEntity) == 1;
        return flag ? groupEntity.getGid(): 0;
    }

    @Override
    public GroupEntity getGroup(int gid) {
        return groupMapper.getGroupByGid(gid);
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
