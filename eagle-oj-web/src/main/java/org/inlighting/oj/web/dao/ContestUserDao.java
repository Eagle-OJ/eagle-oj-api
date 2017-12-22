package org.inlighting.oj.web.dao;

import org.apache.ibatis.session.SqlSession;
import org.inlighting.oj.web.entity.ContestUserEntity;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

/**
 * @author = ygj
 **/
@Repository
public class ContestUserDao {

    public boolean add(SqlSession sqlSession, ContestUserEntity contestUserInfoEntity){
        return sqlSession.insert("contestUser.insert",contestUserInfoEntity) == 1;
    }

    public ContestUserEntity get(SqlSession sqlSession, ContestUserEntity entity) {
        return sqlSession.selectOne("contestUser.select", entity);
    }

    public boolean updateTimesAndData(SqlSession sqlSession, ContestUserEntity entity) {
        return sqlSession.update("contestUser.updateTimesAndData", entity) == 1;
    }
}
