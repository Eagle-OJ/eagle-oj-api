package org.inlighting.oj.web.dao;

import com.github.pagehelper.PageRowBounds;
import org.apache.ibatis.session.SqlSession;
import org.inlighting.oj.web.entity.GroupUserEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author = ygj
 **/

@Repository
public class GroupUserDao {

    public boolean add(SqlSession sqlSession,GroupUserEntity entity){
        int insertNum = sqlSession.insert("groupUser.add", entity);
        return insertNum == 1;
    }

    public GroupUserEntity getMember(SqlSession sqlSession, GroupUserEntity entity) {
        return sqlSession.selectOne("groupUser.get", entity);
    }

    public List<Map<String, Object>> getMembers(SqlSession sqlSession, int gid, PageRowBounds pager) {
        return sqlSession.selectList("groupUser.getMembers", gid, pager);
    }

    public boolean deleteMember(SqlSession sqlSession, GroupUserEntity entity) {
        return sqlSession.delete("groupUser.deleteMember", entity) == 1;
    }
}
