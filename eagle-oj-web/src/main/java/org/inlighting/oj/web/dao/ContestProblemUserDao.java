package org.inlighting.oj.web.dao;

import org.apache.ibatis.session.SqlSession;
import org.inlighting.oj.web.entity.ContestProblemUserEntity;
import org.springframework.stereotype.Repository;

/**
 * @author Smith
 **/
@Repository
public class ContestProblemUserDao {
    public boolean add(SqlSession sqlSession, ContestProblemUserEntity entity) {
        return sqlSession.insert("contestProblemUser.insert", entity) == 1;
    }

    public ContestProblemUserEntity get(SqlSession sqlSession, ContestProblemUserEntity entity) {
        return sqlSession.selectOne("contestProblemUser.select", entity);
    }

    public boolean update(SqlSession sqlSession, ContestProblemUserEntity entity) {
        return sqlSession.update("contestProblemUser.update", entity) == 1;
    }
}
