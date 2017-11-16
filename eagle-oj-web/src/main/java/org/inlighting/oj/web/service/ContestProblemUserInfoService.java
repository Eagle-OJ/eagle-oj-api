package org.inlighting.oj.web.service;

import org.apache.ibatis.session.SqlSession;
import org.inlighting.oj.judge.config.ProblemStatusEnum;
import org.inlighting.oj.web.dao.ContestProblemUserInfoDao;
import org.inlighting.oj.web.entity.ContestProblemUserInfoEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Smith
 **/
@Service
public class ContestProblemUserInfoService {
    private ContestProblemUserInfoDao contestProblemUserInfoDao;

    private SqlSession sqlSession;

    public ContestProblemUserInfoService(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    @Autowired
    public void setContestProblemUserInfoDao(ContestProblemUserInfoDao contestProblemUserInfoDao) {
        this.contestProblemUserInfoDao = contestProblemUserInfoDao;
    }

    public ContestProblemUserInfoEntity get(int cid, int pid, int uid) {
        ContestProblemUserInfoEntity entity = new ContestProblemUserInfoEntity();
        entity.setCid(cid);
        entity.setPid(pid);
        entity.setUid(uid);
        return contestProblemUserInfoDao.getByCidPidUid(sqlSession, entity);
    }

    public boolean add(int cid, int pid, int uid, int score, ProblemStatusEnum status) {
        ContestProblemUserInfoEntity entity = new ContestProblemUserInfoEntity();
        entity.setCid(cid);
        entity.setPid(pid);
        entity.setUid(uid);
        entity.setScore(score);
        entity.setStatus(status);
        return contestProblemUserInfoDao.add(sqlSession, entity);
    }

    public boolean updateStatusScore(int cid, int pid, int uid, int score, ProblemStatusEnum status) {
        ContestProblemUserInfoEntity entity = new ContestProblemUserInfoEntity();
        entity.setCid(cid);
        entity.setPid(pid);
        entity.setUid(uid);
        entity.setScore(score);
        entity.setStatus(status);
        return contestProblemUserInfoDao.updateStatusAndScore(sqlSession, entity);
    }
}
