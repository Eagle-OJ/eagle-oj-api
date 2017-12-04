package org.inlighting.oj.web.dao;

import org.apache.ibatis.session.SqlSession;
import org.inlighting.oj.web.entity.ProblemContestInfoEntity;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Smith
 **/
@Repository
public class ProblemContestInfoDao {

    public boolean add(SqlSession session, ProblemContestInfoEntity entity) {
        return session.insert("problemContestInfo.insertProblemContestInfo", entity) == 1;
    }

    public ProblemContestInfoEntity getProblemContest(SqlSession sqlSession, int pid, int cid) {
        Map<String, Integer> map = new HashMap<>();
        map.put("pid", pid);
        map.put("cid", cid);
        return sqlSession.selectOne("problemContestInfo.getProblemContest", map);
    }

    public List<HashMap<String, Object>> getContestProblems(SqlSession sqlSession, int cid) {
        return sqlSession.selectList("problemContestInfo.getContestProblems", cid);
    }

    public boolean addSubmitTimesByPidAndCid(SqlSession sqlSession, int pid, int cid) {
        Map<String, Integer> map = new HashMap<>();
        map.put("pid", pid);
        map.put("cid", cid);
        return sqlSession.update("problemContestInfo.addSubmitTimesByPidAndCid", map) == 1;
    }

    public boolean addAcceptTimesByPidAndCid(SqlSession sqlSession, int pid, int cid) {
        Map<String, Integer> map = new HashMap<>();
        map.put("pid", pid);
        map.put("cid", cid);
        return sqlSession.update("problemContestInfo.addAcceptTimesByPidAndCid", map) == 1;
    }

    public boolean updateContestProblem(SqlSession sqlSession, ProblemContestInfoEntity entity) {
        return sqlSession.update("problemContestInfo.updateContestProblem", entity) == 1;
    }

    public boolean deleteContestProblem(SqlSession sqlSession, ProblemContestInfoEntity entity) {
        return sqlSession.delete("problemContestInfo.deleteContestProblem", entity) == 1;
    }
}
