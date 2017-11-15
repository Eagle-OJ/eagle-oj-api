package org.inlighting.oj.web.service;

import org.apache.ibatis.session.SqlSession;
import org.inlighting.oj.web.dao.ProblemContestInfoDao;
import org.inlighting.oj.web.entity.ProblemContestInfoEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public ProblemContestInfoEntity getByPidAndBelong(int pid, int cid) {
        return problemContestInfoDao.getByPidAndCid(sqlSession, pid, cid);
    }

    public boolean addProblemInfo(int pid, int cid, int score) {
        ProblemContestInfoEntity problemInfoEntity = new ProblemContestInfoEntity();
        problemInfoEntity.setPid(pid);
        problemInfoEntity.setCid(cid);
        problemInfoEntity.setScore(score);
        return problemContestInfoDao.add(sqlSession, problemInfoEntity);
    }

    public boolean addSubmitTimes(int pid, int cid) {
        return problemContestInfoDao.addSubmitTimesByPidAndCid(sqlSession, pid, cid);
    }

    public boolean addAcceptTimes(int pid, int cid) {
        return problemContestInfoDao.addAcceptTimesByPidAndCid(sqlSession, pid, cid);
    }
}
