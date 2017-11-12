package org.inlighting.oj.web.dao;

import org.apache.ibatis.session.SqlSession;
import org.inlighting.oj.web.entity.GroupUserInfoEntity;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 * @author = ygj
 **/

@Repository
public class GroupUserInfoDao {

    public boolean addGroupUserInfo(SqlSession sqlSession,GroupUserInfoEntity groupUserInfoEntity){
        int insertNum = sqlSession.insert("groupUserInfo.addGroupUserInfo",groupUserInfoEntity);
        return insertNum == 1;
    }

    public GroupUserInfoEntity getByGidAndUid(SqlSession sqlSession,Map<String,Object> map){
        return sqlSession.selectOne("groupUserInfo.getByGidAndUid",map);
    }

    public boolean deleteByGidAndUid(SqlSession sqlSession,Map<String,Object> map){
        int deleteNum = sqlSession.delete("groupUserInfo.deleteByGidAndUid",map);
        return deleteNum == 1;
    }
}
