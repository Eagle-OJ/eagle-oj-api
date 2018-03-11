package com.eagleoj.web.service.impl;

import com.eagleoj.judge.ResultEnum;
import com.eagleoj.web.dao.ContestProblemUserMapper;
import com.eagleoj.web.entity.ContestProblemUserEntity;
import com.eagleoj.web.service.ContestProblemUserService;
import com.eagleoj.web.util.WebUtil;
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
        ContestProblemUserEntity entity = contestProblemUserMapper.getByCidPidUid(cid, pid, uid);
        WebUtil.assertNotNull(entity, "不存在比赛中该用户做题记录");
        return entity;
    }

    @Override
    public List<ContestProblemUserEntity> listAllByCid(int cid) {
        return contestProblemUserMapper.listAllByCid(cid);
    }

    @Override
    public List<Map<String, Object>> listUserDetailInContest(int cid, int uid) {
        return contestProblemUserMapper.listByCidUid(cid, uid);
    }

    @Override
    public void save(int cid, int pid, int uid, int score, ResultEnum status, long solvedTimes, long usedTime) {
        ContestProblemUserEntity entity = new ContestProblemUserEntity();
        entity.setCid(cid);
        entity.setPid(pid);
        entity.setUid(uid);
        if (status != ResultEnum.AC) {
            entity.setWrongTimes(1);
        } else {
            entity.setWrongTimes(0);
        }
        entity.setScore(score);
        entity.setStatus(status);
        entity.setSolvedTime(solvedTimes);
        entity.setUsedTime(usedTime);
        boolean flag = contestProblemUserMapper.save(entity) == 1;
        WebUtil.assertIsSuccess(flag, "比赛题目用户记录保存失败");
    }

    @Override
    public boolean update(int cid, int pid, int uid, int score, ResultEnum status,
                          long solvedTime, long usedTime) {
        ContestProblemUserEntity entity = new ContestProblemUserEntity();
        if (status != ResultEnum.AC) {
            entity.setWrongTimes(1);
        }
        entity.setScore(score);
        entity.setStatus(status);
        entity.setSolvedTime(solvedTime);
        entity.setUsedTime(usedTime);
        return contestProblemUserMapper.updateByCidPidUid(cid, pid, uid, entity) == 1;
    }
}
