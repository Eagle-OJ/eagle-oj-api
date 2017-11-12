package org.inlighting.oj.web.dao;

import org.apache.ibatis.session.SqlSession;
import org.inlighting.oj.web.entity.ProblemEntity;
import org.springframework.stereotype.Repository;


/**
 * @author Smith
 **/
@Repository
public class ProblemDao {

    public boolean addProblem(SqlSession sqlSession, ProblemEntity problemEntity) {
        // 添加问题
        int insertNum = sqlSession.insert("problem.insertProblem",problemEntity);
        return insertNum == 1;
    }

    public ProblemEntity getProblemByPid(SqlSession sqlSession, int pid) {
        // 根据ID查找题目
        return sqlSession.selectOne("problem.getProblemByPid", pid);
    }

    public boolean updateProblemByPid(SqlSession sqlSession, ProblemEntity entity) {
        int updateNum = sqlSession.update("problem.updateProblemByPid", entity);
        return updateNum == 1;
    }

}
