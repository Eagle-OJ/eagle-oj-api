package org.inlighting.oj.web.dao;

import org.apache.ibatis.session.SqlSession;
import org.inlighting.oj.web.entity.ContestUserInfoEntity;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 * @author = ygj
 **/
@Repository
public class ContestUserInfoDao {

    public boolean add(SqlSession sqlSession, ContestUserInfoEntity contestUserInfoEntity){
        int insertNum = sqlSession.insert("contestUserInfo.add",contestUserInfoEntity);
        return insertNum == 1;
    }

    public ContestUserInfoEntity getByUidAndUid(SqlSession sqlSession, Map<String ,Object> map)
    {
        return sqlSession.selectOne("contestUserInfo.getByUidAndUid",map);
    }
}
