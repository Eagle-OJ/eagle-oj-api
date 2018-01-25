package com.eagleoj.web.dao;

import com.eagleoj.web.entity.GroupEntity;
import com.github.pagehelper.PageRowBounds;
import org.apache.ibatis.session.SqlSession;
import com.eagleoj.web.entity.GroupEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author = ygj
 **/

@Repository
public class GroupDao {

    public boolean createGroup(SqlSession sqlSession,GroupEntity groupEntity){
        return sqlSession.insert("group.createGroup",groupEntity) == 1;
    }

    public GroupEntity getGroup(SqlSession sqlSession,int gid){ ;
        return  sqlSession.selectOne("group.getGroup",gid);
    }

    public List<GroupEntity> getGroups(SqlSession sqlSession, int owner, PageRowBounds pager) {
        return sqlSession.selectList("group.getGroups", owner, pager);
    }

    public boolean updateGroup(SqlSession sqlSession, GroupEntity entity) {
        return sqlSession.update("group.updateGroup", entity) == 1;
    }
}
