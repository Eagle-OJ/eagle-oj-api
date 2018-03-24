package com.eagleoj.web.service.impl;

import com.eagleoj.web.controller.exception.WebErrorException;
import com.eagleoj.web.dao.ContestProblemMapper;
import com.eagleoj.web.entity.ContestProblemEntity;
import com.eagleoj.web.entity.ProblemEntity;
import com.eagleoj.web.service.ContestProblemService;
import com.eagleoj.web.service.ContestProblemUserService;
import com.eagleoj.web.service.ProblemService;
import com.eagleoj.web.util.WebUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author Smith
 **/
@Service
public class ContestProblemServiceImpl implements ContestProblemService {

    @Autowired
    private ContestProblemMapper contestProblemMapper;

    @Autowired
    private ContestProblemUserService contestProblemUserService;

    @Autowired
    private ProblemService problemService;

    @Override
    public ContestProblemEntity getContestProblem(int cid, int pid) {
        ContestProblemEntity contestProblemEntity = contestProblemMapper.getByCidPid(cid, pid);
        WebUtil.assertNotNull(contestProblemEntity, "本次比赛中不存在此题目");
        return contestProblemEntity;
    }

    @Override
    public int countContestProblems(int pid) {
        return contestProblemMapper.countByPid(pid);
    }


    @Override
    public List<Map<String, Object>> listContestProblems(int cid) {
        return contestProblemMapper.listByCid(cid);
    }

    @Override
    public List<Map<String, Object>> listContestProblems (int cid, int uid) {
        return contestProblemMapper.listByCidUidWithStatus(cid, uid);
    }

    @Override
    public void saveContestProblem(int cid, int pid, int displayId, int score) {
        try {
            ProblemEntity problemEntity = problemService.getProblem(pid);
        } catch (Exception e) {
            throw new WebErrorException("不存在此题目");
        }

        boolean flag = displayIdIsDuplicate(cid, displayId);
        if (flag) {
            throw new WebErrorException("显示题号重复");
        }

        ContestProblemEntity contestProblemEntity = contestProblemMapper.getByCidPid(cid, pid);
        WebUtil.assertNull(contestProblemEntity, "此题已经添加过了");

        ContestProblemEntity newEntity = new ContestProblemEntity();
        newEntity.setCid(cid);
        newEntity.setPid(pid);
        newEntity.setDisplayId(displayId);
        newEntity.setScore(score);
        boolean res = contestProblemMapper.save(newEntity) == 1;
        WebUtil.assertIsSuccess(res, "题目添加失败");

        // 更新题目被调用记录
        ProblemEntity newProblemEntity = new ProblemEntity();
        newProblemEntity.setUsedTimes(1);
        problemService.updateProblem(pid, newProblemEntity);
    }

    @Override
    public void updateContestProblem(int cid, int pid, int displayId, int score) {
        ContestProblemEntity contestProblemEntity = getContestProblem(cid, pid);

        if (displayId != contestProblemEntity.getDisplayId()) {
            if (displayIdIsDuplicate(cid, displayId)) {
                throw new WebErrorException("显示题号重复");
            }
        }

        if (contestProblemEntity.getScore() != score) {
            // 检查该题目下是否有用户做过，如果做过则不能改变分数
            int users = contestProblemUserService.countContestProblemUser(cid, pid);
            if (users > 0) {
                throw new WebErrorException("该题目已经有用户做过，不能修改分值");
            }
        }

        ContestProblemEntity data = new ContestProblemEntity();
        data.setDisplayId(displayId);
        data.setScore(score);
        boolean flag =  contestProblemMapper.updateByCidPid(cid, pid, data) == 1;
        WebUtil.assertIsSuccess(flag, "题目更新失败");
    }

    @Override
    public void updateContestProblemTimes(int cid, int pid, ContestProblemEntity entity) {
        entity.setCid(cid);
        entity.setPid(pid);
        boolean flag = contestProblemMapper.updateByCidPid(cid, pid, entity) == 1;
        WebUtil.assertIsSuccess(flag, "更新比赛题目次数失败");
    }

    @Override
    public void deleteContestProblem(int cid, int pid) {
        int users = contestProblemUserService.countContestProblemUser(cid, pid);
        if (users > 0) {
            throw new WebErrorException("该题目已经有人做过，无法删除");
        }
        boolean flag = contestProblemMapper.deleteByCidPid(cid, pid) == 1;
        WebUtil.assertIsSuccess(flag, "删除比赛题目失败");
    }

    public void deleteContestProblems(int cid) {
        boolean flag = contestProblemMapper.deleteByCid(cid) > 0;
        WebUtil.assertIsSuccess(flag, "删除比赛题目失败");
    }

    private boolean displayIdIsDuplicate(int cid, int displayId) {
        ContestProblemEntity result = contestProblemMapper.getByCidDisplayId(cid, displayId);
        return result != null;
    }
}
