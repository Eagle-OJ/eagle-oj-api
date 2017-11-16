package org.inlighting.oj.web.dao;

import org.apache.ibatis.session.SqlSession;
import org.inlighting.oj.web.entity.ContestProblemUserInfoEntity;
import org.springframework.stereotype.Repository;

/**
 * @author Smith
 **/
@Repository
public class ContestProblemUserInfoDao {
    public boolean add(SqlSession sqlSession, ContestProblemUserInfoEntity entity) {
        return sqlSession.insert("contestProblemUserInfo.insert", entity) == 1;
    }

    public ContestProblemUserInfoEntity getByCidPidUid(SqlSession sqlSession, ContestProblemUserInfoEntity entity) {
        return sqlSession.selectOne("contestProblemUserInfo.selectByCidPidUid", entity);
    }

    public boolean updateStatusAndScore(SqlSession sqlSession, ContestProblemUserInfoEntity entity) {
        return sqlSession.update("contestProblemUserInfo.updateScoreStatus", entity) == 1;
    }
}
