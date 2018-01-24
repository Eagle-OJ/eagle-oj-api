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

    public List<ProblemEntity> getAuditingProblems(SqlSession sqlSession, PageRowBounds pager) {
        return sqlSession.selectList("problem.getAuditingProblems", null, pager);
    }

    public List<ProblemEntity> getProblemsByUid(SqlSession sqlSession, int uid, PageRowBounds pager) {
        return sqlSession.selectList("problem.getProblemsByUid", uid, pager);
    }

    public List<Map<String, Object>> getSharedProblems(SqlSession sqlSession, Map<String, Object> condition, PageRowBounds pager) {
        return sqlSession.selectList("problem.getSharedProblems", condition, pager);
    }

    public List<Map<String, Object>> getSharedProblemsWithStatus(SqlSession sqlSession, Map<String, Object> condition, PageRowBounds pager) {
        return sqlSession.selectList("problem.getSharedProblemsWithStatus", condition, pager);
    }

    public List<Map<String, Object>> getProblemTags(SqlSession sqlSession, int pid) {
        return sqlSession.selectList("problem.getProblemTags", pid);
    }

    public boolean updateProblemDescription(SqlSession sqlSession, ProblemEntity entity) {
        return sqlSession.update("problem.updateProblemDescription", entity) == 1;
    }

    public boolean updateProblemSetting(SqlSession sqlSession, ProblemEntity entity) {
        return sqlSession.update("problem.updateProblemSetting", entity) == 1;
    }

    public boolean updateProblemTimes(SqlSession sqlSession, ProblemEntity entity) {
        return sqlSession.update("problem.updateProblemTimes", entity) == 1;
    }

    public boolean acceptProblem(SqlSession sqlSession, int pid) {
        return sqlSession.update("problem.acceptProblem", pid) == 1;
    }

    public boolean refuseProblem(SqlSession sqlSession, int pid) {
        return sqlSession.update("problem.refuseProblem", pid) == 1;
    }
}
