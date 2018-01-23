package org.inlighting.oj.web.dao;

import org.apache.ibatis.session.SqlSession;
import org.inlighting.oj.web.entity.ProblemModeratorEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author Smith
 **/
@Repository
public class ProblemModeratorDao {
    public boolean add(SqlSession sqlSession, ProblemModeratorEntity entity) {
        return sqlSession.insert("problemModerator.insert", entity) == 1;
    }

    public ProblemModeratorEntity get(SqlSession sqlSession, ProblemModeratorEntity entity) {
        return sqlSession.selectOne("problemModerator.select", entity);
    }

    public List<Map<String, Object>> getModerators(SqlSession sqlSession, int pid) {
        return sqlSession.selectList("problemModerator.getModerators", pid);
    }

    public boolean delete(SqlSession sqlSession, ProblemModeratorEntity entity) {
        return sqlSession.delete("problemModerator.delete", entity) == 1;
    }
}
