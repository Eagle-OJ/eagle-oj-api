package org.inlighting.oj.web.dao;

import com.alibaba.fastjson.JSONArray;
import com.github.pagehelper.PageRowBounds;
import org.apache.ibatis.session.SqlSession;
import org.inlighting.oj.web.entity.ProblemEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


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

    public List<ProblemEntity> getProblemsByUid(SqlSession sqlSession, int uid, PageRowBounds pager) {
        return sqlSession.selectList("problem.getProblemsByUid", uid, pager);
    }

    public List<ProblemEntity> getSharedProblems(SqlSession sqlSession, PageRowBounds pager) {
        return sqlSession.selectList("problem.getSharedProblems", null, pager);
    }

    public List<Map<String, Object>> getProblemTags(SqlSession sqlSession, int pid) {
        return sqlSession.selectList("problem.getProblemTags", pid);
    }

    public boolean updateProblemDescription(SqlSession sqlSession, ProblemEntity entity) {
        return sqlSession.update("problem.updateProblemDescription", entity) == 1;
    }

    public boolean updateModerators(SqlSession sqlSession, ProblemEntity entity) {
        return sqlSession.update("problem.updateProblemModerators", entity) == 1;
    }

    public boolean addProblemSubmitTimes(SqlSession sqlSession, int pid) {
        return sqlSession.update("problem.addProblemSubmitTimes", pid) == 1;
    }

    public boolean addProblemAcceptTimes(SqlSession sqlSession, int pid) {
        return sqlSession.update("problem.addProblemAcceptTimes", pid) == 1;
    }
}
