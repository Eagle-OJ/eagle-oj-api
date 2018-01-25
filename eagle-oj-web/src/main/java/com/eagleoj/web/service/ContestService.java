package com.eagleoj.web.service;

import com.eagleoj.web.postman.MessageQueue;
import com.eagleoj.web.postman.task.CloseNormalContestTask;
import com.github.pagehelper.PageRowBounds;
import org.apache.ibatis.session.SqlSession;
import com.eagleoj.web.dao.ContestDao;
import com.eagleoj.web.entity.ContestEntity;
import com.eagleoj.web.postman.task.CloseOfficialContestTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

/**
 * @author Smith
 **/
@Service
public class ContestService {

    @Autowired
    private SqlSession sqlSession;

    @Autowired
    private ContestDao contestDao ;

    @Autowired
    private MessageQueue messageQueue;

    public int addContest(String name, int owner, String slogan, String description,
                          long startTime, long endTime, Long totalTime, String password,
                          int type, long createTime) {
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
        contestEntity.setCreateTime(createTime);
        boolean result = contestDao.addContest(sqlSession,contestEntity);
        return result ? contestEntity.getCid() : 0;
    }

    public List<ContestEntity> getUserContests(int uid, PageRowBounds pager) {
        return contestDao.getUserContests(sqlSession, uid, pager);
    }

    // 进行比赛校验
    public ContestEntity getContestByCid(int cid) {
        ContestEntity contestEntity = contestDao.getContestByCid(sqlSession, cid);
        checkContestValid(contestEntity);
        return contestEntity;
    }

    public boolean deleteContestByCid(int cid){
        //根据比赛ID来删除比赛
        return contestDao.deleteContestByCid(sqlSession,cid);
    }

    public boolean updateContestDescription(int cid, String name, String slogan, String description, long startTime,
                                            long endTime, Long totalTime, String password, int type) {
        ContestEntity contestEntity = new ContestEntity();
        contestEntity.setCid(cid);
        contestEntity.setName(name);
        contestEntity.setSlogan(slogan);
        contestEntity.setDescription(description);
        contestEntity.setStartTime(startTime);
        contestEntity.setEndTime(endTime);
        contestEntity.setTotalTime(totalTime);
        contestEntity.setPassword(password);
        contestEntity.setType(type);
        return contestDao.updateContestDescription(sqlSession, contestEntity);
    }

    public boolean updateContestStatus(int cid, int status) {
        ContestEntity contestEntity = new ContestEntity();
        contestEntity.setCid(cid);
        contestEntity.setStatus(status);
        return contestDao.updateContestStatus(sqlSession, contestEntity);
    }

    public List<Map<String, Object>> getValidContests(PageRowBounds pager){
        List<Map<String, Object>> contests = contestDao.getValidContests(sqlSession, pager);
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
        if (updateContestStatus(cid, 2)) {
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
