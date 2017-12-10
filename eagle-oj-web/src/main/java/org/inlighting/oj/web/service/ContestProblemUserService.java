package org.inlighting.oj.web.service;

import org.apache.ibatis.session.SqlSession;
import org.inlighting.oj.judge.ResultEnum;
import org.inlighting.oj.web.dao.ContestProblemUserDao;
import org.inlighting.oj.web.entity.ContestProblemUserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Smith
 **/
@Service
public class ContestProblemUserService {
    private ContestProblemUserDao contestProblemUserDao;

    private SqlSession sqlSession;

    public ContestProblemUserService(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    @Autowired
    public void setContestProblemUserDao(ContestProblemUserDao contestProblemUserDao) {
        this.contestProblemUserDao = contestProblemUserDao;
    }

    public ContestProblemUserEntity get(int cid, int pid, int uid) {
        ContestProblemUserEntity entity = new ContestProblemUserEntity();
        entity.setCid(cid);
        entity.setPid(pid);
        entity.setUid(uid);
        return contestProblemUserDao.get(sqlSession, entity);
    }

    public boolean add(int cid, int pid, int uid, int score, ResultEnum status) {
        ContestProblemUserEntity entity = new ContestProblemUserEntity();
        entity.setCid(cid);
        entity.setPid(pid);
        entity.setUid(uid);
        entity.setScore(score);
        entity.setStatus(status);
        return contestProblemUserDao.add(sqlSession, entity);
    }

    public boolean updateScoreStatus(int cid, int pid, int uid, int score, ResultEnum status) {
        ContestProblemUserEntity entity = new ContestProblemUserEntity();
        entity.setCid(cid);
        entity.setPid(pid);
        entity.setUid(uid);
        entity.setScore(score);
        entity.setStatus(status);
        return contestProblemUserDao.update(sqlSession, entity);
    }
}
