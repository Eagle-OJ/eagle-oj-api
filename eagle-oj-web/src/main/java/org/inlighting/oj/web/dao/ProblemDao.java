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
        if(insertNum == 1){
            return true;
        }
        else {
            return false;
        }
    }

    public ProblemEntity getProblemByPid(SqlSession sqlSession,int pid) {
        // 根据ID查找题目
        ProblemEntity problemEntity =  sqlSession.selectOne("problem.getProblemByPid",pid);
        return problemEntity;
    }

    public boolean updateProblem(int pid, ProblemEntity entity) {
        // todo
        return false;
    }
}
