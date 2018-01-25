package com.eagleoj.web.service.impl;

import com.eagleoj.judge.ResultEnum;
import com.eagleoj.web.dao.ContestProblemUserDao;
import com.eagleoj.web.dao.ContestProblemUserMapper;
import com.eagleoj.web.entity.ContestProblemUserEntity;
import com.eagleoj.web.service.ContestProblemUserService;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author Smith
 **/
@Service
public class ContestProblemUserServiceImpl implements ContestProblemUserService {

    @Autowired
    private ContestProblemUserMapper contestProblemUserMapper;

    @Override
    public ContestProblemUserEntity getByCidPidUid(int cid, int pid, int uid) {
        return contestProblemUserMapper.getByCidPidUid(cid, pid, uid);
    }

    @Override
    public List<ContestProblemUserEntity> listAllByCid(int cid) {
        return contestProblemUserMapper.listAllByCid(cid);
    }

    @Override
    public List<Map<String, Object>> listNormalContestRankByCid(int cid) {
        return contestProblemUserMapper.listNormalContestRankByCid(cid);
    }

    @Override
    public boolean save(int cid, int pid, int uid, int score, ResultEnum status, long solvedTimes, long usedTime) {
        ContestProblemUserEntity entity = new ContestProblemUserEntity();
        entity.setCid(cid);
        entity.setPid(pid);
        entity.setUid(uid);
        entity.setScore(score);
        entity.setStatus(status);
        entity.setSolvedTime(solvedTimes);
        entity.setUsedTime(usedTime);
        return contestProblemUserMapper.save(entity) == 1;
    }

    @Override
    public boolean update(int cid, int pid, int uid, int score, ResultEnum status,
                          long solvedTime, long usedTime) {
        ContestProblemUserEntity entity = new ContestProblemUserEntity();
        entity.setScore(score);
        entity.setStatus(status);
        entity.setSolvedTime(solvedTime);
        entity.setUsedTime(usedTime);
        return contestProblemUserMapper.updateByCidPidUid(cid, pid, uid, entity) == 1;
    }
}
