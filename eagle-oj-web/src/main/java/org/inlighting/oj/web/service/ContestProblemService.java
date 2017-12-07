package org.inlighting.oj.web.service;

import org.apache.ibatis.session.SqlSession;
import org.inlighting.oj.web.dao.ContestProblemDao;
import org.inlighting.oj.web.entity.ContestProblemEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * @author Smith
 **/
@Service
public class ContestProblemService {

    private ContestProblemDao problemContestInfoDao;

    private final SqlSession sqlSession;

    public ContestProblemService(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    @Autowired
    public void setProblemContestInfoDao(ContestProblemDao problemContestInfoDao) {
        this.problemContestInfoDao = problemContestInfoDao;
    }

    public ContestProblemEntity getContestProblem(int pid, int cid) {
        ContestProblemEntity contestProblemEntity = new ContestProblemEntity();
        contestProblemEntity.setPid(pid);
        contestProblemEntity.setCid(cid);
        return problemContestInfoDao.getContestProblem(sqlSession, contestProblemEntity);
    }

    public List<HashMap<String, Object>> getContestProblems(int cid) {
        return problemContestInfoDao.getContestProblems(sqlSession, cid);
    }

    public boolean addProblemInfo(int pid, int cid, int displayId, int score) {
        ContestProblemEntity contestProblemEntity = new ContestProblemEntity();
        contestProblemEntity.setPid(pid);
        contestProblemEntity.setCid(cid);
        contestProblemEntity.setDisplayId(displayId);
        contestProblemEntity.setScore(score);
        return problemContestInfoDao.add(sqlSession, contestProblemEntity);
    }

    public boolean updateContestProblem(int cid, int pid, int displayId, int score) {
        ContestProblemEntity entity = new ContestProblemEntity();
        entity.setCid(cid);
        entity.setPid(pid);
        entity.setDisplayId(displayId);
        entity.setScore(score);
        return problemContestInfoDao.updateContestProblem(sqlSession, entity);
    }

    public boolean deleteContestProblem(int cid, int pid) {
        ContestProblemEntity entity = new ContestProblemEntity();
        entity.setCid(cid);
        entity.setPid(pid);
        return problemContestInfoDao.deleteContestProblem(sqlSession, entity);
    }

    public boolean displayIdIsDuplicate(int cid, int displayId) {
        ContestProblemEntity entity = new ContestProblemEntity();
        entity.setCid(cid);
        entity.setDisplayId(displayId);
        ContestProblemEntity result = problemContestInfoDao.getContestProblem(sqlSession, entity);
        return result != null;
    }

    public boolean addSubmitTimes(int pid, int cid) {
        return problemContestInfoDao.addSubmitTimesByPidAndCid(sqlSession, pid, cid);
    }

    public boolean addAcceptTimes(int pid, int cid) {
        return problemContestInfoDao.addAcceptTimesByPidAndCid(sqlSession, pid, cid);
    }
}
