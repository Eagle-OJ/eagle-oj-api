package com.eagleoj.web.service.impl;

import com.eagleoj.web.controller.exception.WebErrorException;
import com.eagleoj.web.dao.ContestMapper;
import com.eagleoj.web.data.status.ContestStatus;
import com.eagleoj.web.entity.ContestEntity;
import com.eagleoj.web.postman.TaskQueue;
import com.eagleoj.web.postman.task.CloseNormalContestTask;
import com.eagleoj.web.postman.task.CloseOfficialContestTask;
import com.eagleoj.web.service.ContestProblemService;
import com.eagleoj.web.service.ContestService;
import com.eagleoj.web.service.ContestUserService;
import com.eagleoj.web.service.async.AsyncTaskService;
import com.eagleoj.web.util.WebUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private AsyncTaskService asyncTaskService;

    @Autowired
    private ContestUserService contestUserService;

    @Autowired
    private ContestProblemService contestProblemService;

    @Override
    public int saveContest(String name, int owner, int group, String slogan, String description,
                          long startTime, long endTime, Long totalTime, String password,
                          int type) {
        // 添加比赛
        ContestEntity contestEntity = new ContestEntity();
        contestEntity.setName(name);
        contestEntity.setOwner(owner);
        contestEntity.setGroup(group);
        contestEntity.setSlogan(slogan);
        contestEntity.setDescription(description);
        contestEntity.setStartTime(startTime);
        contestEntity.setEndTime(endTime);
        contestEntity.setTotalTime(totalTime);
        contestEntity.setPassword(password);
        contestEntity.setOfficial(0);
        contestEntity.setType(type);
        contestEntity.setStatus(ContestStatus.EDITING.getNumber());
        contestEntity.setCreateTime(System.currentTimeMillis());
        boolean flag = contestMapper.save(contestEntity) == 1;
        WebUtil.assertIsSuccess(flag, "比赛创建失败");
        return contestEntity.getCid();
    }

    @Override
    public int countGroupContests(int gid) {
        return contestMapper.countByGid(gid);
    }

    @Override
    public int countContests() {
        return contestMapper.count();
    }

    @Transactional
    @Override
    public void deleteContest(int cid) {
        int users = contestUserService.countContestUsers(cid);
        if (users > 0) {
            throw new WebErrorException("该比赛已经有人参加，无法删除");
        }

        if (contestProblemService.countContestProblems(cid) > 0) {
            contestProblemService.deleteContestProblems(cid);
        }

        boolean flag = contestMapper.deleteByCid(cid) == 1;
        WebUtil.assertIsSuccess(flag, "删除比赛失败");
    }

    @Override
    public List<Map<String, Object>> listOpenedContests() {
        List<Map<String, Object>> list = contestMapper.listContests(false);
        for (Map<String, Object> each: list) {
            checkContestValid(each);
        }
        return list;
    }

    @Override
    public List<Map<String, Object>> listAllContests() {
        List<Map<String, Object>> list = contestMapper.listContests(true);
        for (Map<String, Object> each: list) {
            checkContestValid(each);
        }
        return list;
    }

    @Override
    public List<ContestEntity> listGroupContests(int gid, ContestStatus status) {
        Integer i = status==null? null: status.getNumber();
        List<ContestEntity> list = contestMapper.listContestsByGid(gid, i);
        for (ContestEntity entity: list) {
            checkContestValid(entity);
        }
        return list;
    }

    @Override
    public List<ContestEntity> listUserContests(int uid, int gid) {
        List<ContestEntity> list = contestMapper.listByUidGid(uid, gid);
        for (ContestEntity each: list) {
            checkContestValid(each);
        }
        return list;
    }

    @Override
    public ContestEntity getContest(int cid) {
        ContestEntity contestEntity = contestMapper.getByCid(cid);
        WebUtil.assertNotNull(contestEntity, "不存在此比赛");
        checkContestValid(contestEntity);
        return contestEntity;
    }

    @Override
    public void updateContest(int cid, String name, String slogan, String description, long startTime,
                       long endTime, Long totalTime, String password, int type, int status) {
        ContestEntity contestEntity = new ContestEntity();
        contestEntity.setName(name);
        contestEntity.setSlogan(slogan);
        contestEntity.setDescription(description);
        contestEntity.setStartTime(startTime);
        contestEntity.setEndTime(endTime);
        contestEntity.setTotalTime(totalTime);
        contestEntity.setPassword(password);
        contestEntity.setType(type);
        contestEntity.setStatus(status);
        updateContest(cid, contestEntity);
    }

    private void updateContest(int cid, ContestEntity contestEntity) {
        WebUtil.assertIsSuccess(contestMapper.updateByCid(cid, contestEntity) == 1, "更新比赛失败");
    }

    private void checkContestValid(ContestEntity contestEntity) {
        if (contestEntity.getStatus() == ContestStatus.USING.getNumber()) {
            if (contestEntity.getEndTime()<System.currentTimeMillis()) {
                closeContest(contestEntity.getCid(), contestEntity.getOfficial());
                contestEntity.setStatus(ContestStatus.CLOSED.getNumber());
            }
        }
    }

    private void checkContestValid(Map<String, Object> contest) {
        if ((int)contest.get("status") == ContestStatus.USING.getNumber()) {
            BigInteger bigInteger = (BigInteger)contest.get("end_time");
            if (bigInteger.longValue()<System.currentTimeMillis()) {
                Long cid = (Long)contest.get("cid");
                int official = (int) contest.get("official");
                closeContest(cid.intValue(), official);
                contest.replace("status", ContestStatus.CLOSED.getNumber());
            }
        }
    }

    private void closeContest(int cid, int official) {
        ContestEntity contestEntity = new ContestEntity();
        contestEntity.setStatus(ContestStatus.CLOSED.getNumber());
        updateContest(cid, contestEntity);
        // 关闭官方比赛
        if (official == 1) {
            asyncTaskService.closeOfficialContest(cid);
        } else {
            asyncTaskService.closeNormalContest(cid);
        }
    }
}
