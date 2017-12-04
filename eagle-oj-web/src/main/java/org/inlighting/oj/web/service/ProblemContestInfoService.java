package org.inlighting.oj.web.service;

import org.apache.ibatis.session.SqlSession;
import org.inlighting.oj.web.dao.ProblemContestInfoDao;
import org.inlighting.oj.web.entity.ProblemContestInfoEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * @author Smith
 **/
@Service
public class ProblemContestInfoService {

    private ProblemContestInfoDao problemContestInfoDao;

    private final SqlSession sqlSession;

    public ProblemContestInfoService(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    @Autowired
    public void setProblemContestInfoDao(ProblemContestInfoDao problemContestInfoDao) {
        this.problemContestInfoDao = problemContestInfoDao;
    }

    public ProblemContestInfoEntity getProblemContest(int pid, int cid) {
        return problemContestInfoDao.getProblemContest(sqlSession, pid, cid);
    }

    public List<HashMap<String, Object>> getContestProblems(int cid) {
        return problemContestInfoDao.getContestProblems(sqlSession, cid);
    }

    public boolean addProblemInfo(int pid, int cid, int score) {
        ProblemContestInfoEntity problemInfoEntity = new ProblemContestInfoEntity();
        problemInfoEntity.setPid(pid);
        problemInfoEntity.setCid(cid);
        problemInfoEntity.setScore(score);
        return problemContestInfoDao.add(sqlSession, problemInfoEntity);
    }

    public boolean updateContestProblem(int cid, int pid, int score) {
        ProblemContestInfoEntity entity = new ProblemContestInfoEntity();
        entity.setCid(cid);
        entity.setPid(pid);
        entity.setScore(score);
        return problemContestInfoDao.updateContestProblem(sqlSession, entity);
    }

    public boolean deleteContestProblem(int cid, int pid) {
        ProblemContestInfoEntity entity = new ProblemContestInfoEntity();
        entity.setCid(cid);
        entity.setPid(pid);
        return problemContestInfoDao.deleteContestProblem(sqlSession, entity);
    }

    public boolean addSubmitTimes(int pid, int cid) {
        return problemContestInfoDao.addSubmitTimesByPidAndCid(sqlSession, pid, cid);
    }

    public boolean addAcceptTimes(int pid, int cid) {
        return problemContestInfoDao.addAcceptTimesByPidAndCid(sqlSession, pid, cid);
    }
}
