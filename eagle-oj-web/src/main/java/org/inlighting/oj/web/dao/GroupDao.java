package org.inlighting.oj.web.dao;

import org.apache.ibatis.session.SqlSession;
import org.inlighting.oj.web.entity.GroupEntity;
import org.springframework.stereotype.Repository;

/**
 * @author = ygj
 **/

@Repository
public class GroupDao {

    public boolean createGroup(SqlSession sqlSession,GroupEntity groupEntity){
        int insertNum = sqlSession.insert("group.createGroup",groupEntity);
        return insertNum == 1 ;
    }

    public GroupEntity getGroupByGid(SqlSession sqlSession,int gid){ ;
        return  sqlSession.selectOne("getGroupByGid",gid);
    }
}
