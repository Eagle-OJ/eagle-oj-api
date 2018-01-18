package org.inlighting.oj.web.service;

import com.github.pagehelper.PageRowBounds;
import org.apache.ibatis.session.SqlSession;
import org.inlighting.oj.web.dao.GroupDao;
import org.inlighting.oj.web.entity.GroupEntity;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Smith
 **/
@Service
public class GroupService {

    @Autowired
    private GroupDao groupDao;

    @Autowired
    private SqlSession sqlSession;

    public int createGroup(int owner, String name, String password, long createTime) {
        GroupEntity groupEntity = new GroupEntity();
        groupEntity.setOwner(owner);
        groupEntity.setPassword(password);
        groupEntity.setName(name);
        groupEntity.setCreateTime(createTime);
        boolean flag= groupDao.createGroup(sqlSession,groupEntity);
        return flag ? groupEntity.getGid():0;
    }

    public GroupEntity getGroup(int gid) {
        return groupDao.getGroup(sqlSession,gid);
    }

    public List<GroupEntity> getGroups(int owner, PageRowBounds pager) {
        return groupDao.getGroups(sqlSession, owner, pager);
    }

    public boolean updateGroup(int gid, String name, String password) {
        GroupEntity entity = new GroupEntity();
        entity.setGid(gid);
        entity.setName(name);
        entity.setPassword(password);
        return groupDao.updateGroup(sqlSession, entity);
    }
}
