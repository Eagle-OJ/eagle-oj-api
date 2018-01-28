package com.eagleoj.web.service.impl;

import com.eagleoj.web.controller.exception.WebErrorException;
import com.eagleoj.web.dao.ContestProblemMapper;
import com.eagleoj.web.entity.ContestProblemEntity;
import com.eagleoj.web.entity.ProblemEntity;
import com.eagleoj.web.service.ContestProblemService;
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
    private ProblemService problemService;

    @Override
    public ContestProblemEntity getContestProblem(int cid, int pid) {
        return contestProblemMapper.getByCidPid(cid, pid);
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
        ProblemEntity problemEntity = problemService.getProblem(cid);
        WebUtil.assertNotNull(problemEntity, "题目不存在");

        boolean flag = displayIdIsDuplicate(cid, displayId);
        if (flag) {
            throw new WebErrorException("显示题号重复");
        }

        ContestProblemEntity contestProblemEntity = getContestProblem(cid, pid);
        WebUtil.assertNull(contestProblemEntity, "此题已经添加过了");

        ContestProblemEntity newEntity = new ContestProblemEntity();
        newEntity.setCid(cid);
        newEntity.setPid(pid);
        newEntity.setDisplayId(displayId);
        newEntity.setScore(score);
        boolean res = contestProblemMapper.save(newEntity) == 1;
        WebUtil.assertIsSuccess(res, "题目添加失败");
    }

    @Override
    public boolean updateContestProblemInfo(int cid, int pid, int displayId, int score) {
        ContestProblemEntity data = new ContestProblemEntity();
        data.setCid(cid);
        data.setPid(pid);
        data.setDisplayId(displayId);
        data.setScore(score);
        return contestProblemMapper.updateByCidPid(cid, pid, data) == 1;
    }

    @Override
    public boolean updateContestProblemTimes(int cid, int pid, ContestProblemEntity entity) {
        entity.setCid(cid);
        entity.setPid(pid);
        return contestProblemMapper.updateByCidPid(cid, pid, entity) == 1;
    }

    @Override
    public boolean deleteByCidPid(int cid, int pid) {
        return contestProblemMapper.deleteByCidPid(cid, pid) == 1;
    }

    private boolean displayIdIsDuplicate(int cid, int displayId) {
        ContestProblemEntity result = contestProblemMapper.getByCidDisplayId(cid, displayId);
        return result != null;
    }
}
