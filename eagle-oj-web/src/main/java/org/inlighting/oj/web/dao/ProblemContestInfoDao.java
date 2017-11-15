package org.inlighting.oj.web.dao;

import org.apache.ibatis.session.SqlSession;
import org.inlighting.oj.web.entity.ProblemContestInfoEntity;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Smith
 **/
@Repository
public class ProblemContestInfoDao {

    public boolean add(SqlSession session, ProblemContestInfoEntity entity) {
        return session.insert("problemContestInfo.insertProblemContestInfo", entity) == 1;
    }

    public ProblemContestInfoEntity getByPidAndCid(SqlSession sqlSession, int pid, int cid) {
        Map<String, Integer> map = new HashMap<>();
        map.put("pid", pid);
        map.put("cid", cid);
        return sqlSession.selectOne("problemContestInfo.getProblemContestInfoByPidAndCid", map);
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
}
