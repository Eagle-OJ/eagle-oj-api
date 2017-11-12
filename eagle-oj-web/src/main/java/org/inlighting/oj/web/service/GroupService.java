package org.inlighting.oj.web.service;

import org.apache.ibatis.session.SqlSession;
import org.inlighting.oj.web.dao.GroupDao;
import org.inlighting.oj.web.entity.GroupEntity;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Smith
 **/
@Service
public class GroupService {

    private GroupDao groupDao;

    private final SqlSession sqlSession;

    public GroupService(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    @Autowired
    public void setGroupDao(GroupDao groupDao) {
        this.groupDao = groupDao;
    }

    public int createGroup(int owner, int cover, String name, String password, long createTime) {
        // 添加小组

        GroupEntity groupEntity = new GroupEntity();
        groupEntity.setCover(cover);
        groupEntity.setOwner(owner);
        groupEntity.setPassword(password);
        groupEntity.setName(name);
        groupEntity.setCreateTime(createTime);
        boolean flag= groupDao.createGroup(sqlSession,groupEntity);
        return flag ? groupEntity.getGid():0;
    }

    public GroupEntity getByGid(int gid) {
        // 查找小组
        return groupDao.getGroupByGid(sqlSession,gid);
    }
}
