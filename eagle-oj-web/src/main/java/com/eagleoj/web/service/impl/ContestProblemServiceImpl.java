package com.eagleoj.web.service.impl;

import com.eagleoj.web.dao.ContestProblemMapper;
import com.eagleoj.web.entity.ContestProblemEntity;
import com.eagleoj.web.service.ContestProblemService;
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

    @Override
    public ContestProblemEntity getContestProblem(int cid, int pid) {
        return contestProblemMapper.getByCidPid(cid, pid);
    }

    @Override
    public List<Map<String, Object>> listContestProblemsByCid(int cid) {
        return contestProblemMapper.listByCid(cid);
    }

    @Override
    public List<Map<String, Object>> listContestProblemsWithUserStatus (int cid, int uid) {
        return contestProblemMapper.listByCidUidWithStatus(cid, uid);
    }

    @Override
    public boolean save(int pid, int cid, int displayId, int score) {
        ContestProblemEntity contestProblemEntity = new ContestProblemEntity();
        contestProblemEntity.setPid(pid);
        contestProblemEntity.setCid(cid);
        contestProblemEntity.setDisplayId(displayId);
        contestProblemEntity.setScore(score);
        return contestProblemMapper.save(contestProblemEntity) == 1;
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

    @Override
    public boolean displayIdIsDuplicate(int cid, int displayId) {
        ContestProblemEntity result = contestProblemMapper.getByCidDisplayId(cid, displayId);
        return result != null;
    }
}
