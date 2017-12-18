package org.inlighting.oj.web.dao;

import org.apache.ibatis.session.SqlSession;
import org.inlighting.oj.web.entity.ContestProblemUserEntity;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public List<ContestProblemUserEntity> getAll(SqlSession sqlSession, int cid) {
        return sqlSession.selectList("contestProblemUser.selectAll", cid);
    }

    public List<Map<String, Object>> getNormalContestRank(SqlSession sqlSession, int cid) {
        return sqlSession.selectList("contestProblemUser.getNormalContestRank", cid);
    }

    public boolean update(SqlSession sqlSession, ContestProblemUserEntity entity) {
        return sqlSession.update("contestProblemUser.update", entity) == 1;
    }
}
