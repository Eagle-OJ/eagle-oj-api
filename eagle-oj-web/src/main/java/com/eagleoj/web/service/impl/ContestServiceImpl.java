package com.eagleoj.web.service.impl;

import com.eagleoj.web.dao.ContestMapper;
import com.eagleoj.web.entity.ContestEntity;
import com.eagleoj.web.postman.MessageQueue;
import com.eagleoj.web.postman.task.CloseNormalContestTask;
import com.eagleoj.web.postman.task.CloseOfficialContestTask;
import com.eagleoj.web.service.ContestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

/**
 * @author Smith
 **/
@Service
public class ContestServiceImpl implements ContestService {

    @Autowired
    private ContestMapper contestMapper;

    @Autowired
    private MessageQueue messageQueue;

    @Override
    public int save(String name, int owner, String slogan, String description,
                          long startTime, long endTime, Long totalTime, String password,
                          int type) {
        // 添加比赛
        ContestEntity contestEntity = new ContestEntity();
        contestEntity.setName(name);
        contestEntity.setOwner(owner);
        contestEntity.setSlogan(slogan);
        contestEntity.setDescription(description);
        contestEntity.setStartTime(startTime);
        contestEntity.setEndTime(endTime);
        contestEntity.setTotalTime(totalTime);
        contestEntity.setPassword(password);
        contestEntity.setOfficial(0);
        contestEntity.setType(type);
        contestEntity.setStatus(0);
        contestEntity.setCreateTime(System.currentTimeMillis());
        boolean flag = contestMapper.save(contestEntity) == 1;
        return flag ? contestEntity.getCid() : 0;
    }

    @Override
    public List<ContestEntity> listByUid(int uid) {
        return contestMapper.listByUid(uid);
    }

    // 取出时自动进行比赛校验
    @Override
    public ContestEntity getByCid(int cid) {
        ContestEntity contestEntity = contestMapper.getByCid(cid);
        checkContestValid(contestEntity);
        return contestEntity;
    }

    @Override
    public boolean deleteByCid(int cid) {
        return contestMapper.deleteByCid(cid) == 1;
    }

    @Override
    public boolean updateDescriptionByCid (int cid, String name, String slogan, String description, long startTime,
                                            long endTime, Long totalTime, String password, int type) {
        ContestEntity contestEntity = new ContestEntity();
        contestEntity.setName(name);
        contestEntity.setSlogan(slogan);
        contestEntity.setDescription(description);
        contestEntity.setStartTime(startTime);
        contestEntity.setEndTime(endTime);
        contestEntity.setTotalTime(totalTime);
        contestEntity.setPassword(password);
        contestEntity.setType(type);
        return contestMapper.updateDescriptionByCid(cid, contestEntity) == 1;
    }

    @Override
    public boolean updateStatusByCid(int cid, int status) {
        ContestEntity contestEntity = new ContestEntity();
        contestEntity.setStatus(status);
        return contestMapper.updateStatusByCid(cid, status) == 1;
    }

    @Override
    public List<Map<String, Object>> listValid() {
        List<Map<String, Object>> contests = contestMapper.listValid();
        for (Map<String, Object> contest: contests) {
            checkContestValid(contest);
        }
        return contests;
    }

    private void checkContestValid(ContestEntity contestEntity) {
        if (contestEntity!=null && contestEntity.getStatus() == 1) {
            if (contestEntity.getEndTime()<System.currentTimeMillis()) {
                closeContest(contestEntity.getCid(), contestEntity.getOfficial());
                contestEntity.setStatus(2);
            }
        }
    }

    private void checkContestValid(Map<String, Object> contest) {
        if ((int)contest.get("status") == 1) {
            BigInteger bigInteger = (BigInteger)contest.get("end_time");
            if (bigInteger.longValue()<System.currentTimeMillis()) {
                Long cid = (Long)contest.get("cid");
                int official = (int) contest.get("official");
                closeContest(cid.intValue(), official);
                contest.replace("status", 2);
            }
        }
    }

    private void closeContest(int cid, int official) {
        final int CLOSE_STATUS = 2;
        if (updateStatusByCid(cid, CLOSE_STATUS)) {
            // 关闭官方比赛
            if (official == 1) {
                CloseOfficialContestTask task = new CloseOfficialContestTask(cid, 2);
                messageQueue.addTask(task);
            } else {
                CloseNormalContestTask task = new CloseNormalContestTask(cid, 1);
                messageQueue.addTask(task);
            }
        }
    }
}
