package com.eagleoj.web.service;

import org.apache.ibatis.session.SqlSession;
import com.eagleoj.judge.ResultEnum;
import com.eagleoj.web.dao.ContestProblemUserDao;
import com.eagleoj.web.entity.ContestProblemUserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author Smith
 **/
@Service
public class ContestProblemUserService {

    @Autowired
    private ContestProblemUserDao contestProblemUserDao;

    @Autowired
    private SqlSession sqlSession;

    public ContestProblemUserEntity get(int cid, int pid, int uid) {
        ContestProblemUserEntity entity = new ContestProblemUserEntity();
        entity.setCid(cid);
        entity.setPid(pid);
        entity.setUid(uid);
        return contestProblemUserDao.get(sqlSession, entity);
    }

    public List<ContestProblemUserEntity> getAll(int cid) {
        return contestProblemUserDao.getAll(sqlSession, cid);
    }

    public List<Map<String, Object>> getNormalContestRank(int cid) {
        return contestProblemUserDao.getNormalContestRank(sqlSession, cid);
    }

    public boolean add(int cid, int pid, int uid, int score, ResultEnum status, long solvedTimes, long usedTime) {
        ContestProblemUserEntity entity = new ContestProblemUserEntity();
        entity.setCid(cid);
        entity.setPid(pid);
        entity.setUid(uid);
        entity.setScore(score);
        entity.setStatus(status);
        entity.setSolvedTime(solvedTimes);
        entity.setUsedTime(usedTime);
        return contestProblemUserDao.add(sqlSession, entity);
    }

    public boolean update(int cid, int pid, int uid, int score, ResultEnum status, long solvedTime, long usedTime) {
        ContestProblemUserEntity entity = new ContestProblemUserEntity();
        entity.setCid(cid);
        entity.setPid(pid);
        entity.setUid(uid);
        entity.setScore(score);
        entity.setStatus(status);
        entity.setSolvedTime(solvedTime);
        entity.setUsedTime(usedTime);
        return contestProblemUserDao.update(sqlSession, entity);
    }
}
