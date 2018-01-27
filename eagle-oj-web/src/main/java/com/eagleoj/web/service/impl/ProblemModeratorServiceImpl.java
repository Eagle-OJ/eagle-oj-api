package com.eagleoj.web.service.impl;

import com.eagleoj.web.dao.ProblemModeratorMapper;
import com.eagleoj.web.entity.ProblemModeratorEntity;
import com.eagleoj.web.service.ProblemModeratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author Smith
 **/
@Service
public class ProblemModeratorServiceImpl implements ProblemModeratorService {

    @Autowired
    private ProblemModeratorMapper problemModeratorMapper;

    @Override
    public boolean save(int pid, int uid) {
        ProblemModeratorEntity entity = new ProblemModeratorEntity();
        entity.setUid(uid);
        entity.setPid(pid);
        return problemModeratorMapper.save(entity) == 1;
    }

    @Override
    public boolean isExistModeratorInProblem(int pid, int uid) {
        return get(pid, uid) != null;
    }

    @Override
    public ProblemModeratorEntity get(int pid, int uid) {
        return problemModeratorMapper.getByPidUid(pid, uid);
    }

    @Override
    public List<Map<String, Object>> listProblemModerators(int pid) {
        return problemModeratorMapper.listModeratorsByPid(pid);
    }

    public boolean delete(int pid, int uid) {
        return problemModeratorMapper.deleteByPidUid(pid, uid) == 1;
    }
}
