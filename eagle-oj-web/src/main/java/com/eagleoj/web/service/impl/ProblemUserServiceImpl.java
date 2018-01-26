package com.eagleoj.web.service.impl;

import com.eagleoj.judge.ResultEnum;
import com.eagleoj.web.dao.ProblemUserMapper;
import com.eagleoj.web.entity.ProblemUserEntity;
import com.eagleoj.web.service.ProblemUserService;
import com.github.pagehelper.PageRowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author Smith
 **/
@Service
public class ProblemUserServiceImpl implements ProblemUserService {

    @Autowired
    private ProblemUserMapper problemUserMapper;

    @Override
    public boolean save(int pid, int uid, ResultEnum status) {
        ProblemUserEntity problemUserEntity = new ProblemUserEntity();
        problemUserEntity.setPid(pid);
        problemUserEntity.setUid(uid);
        problemUserEntity.setStatus(status);
        return problemUserMapper.save(problemUserEntity) == 1;
    }

    @Override
    public ProblemUserEntity get(int pid, int uid) {
        return problemUserMapper.getByPidUid(pid, uid);
    }

    @Override
    public List<Map<String, Object>> listUserProblemHistory(int uid) {
        return problemUserMapper.listUserProblemsByUid(uid);
    }

    @Override
    public boolean updateByPid(int pid, int uid, ResultEnum result) {
        ProblemUserEntity problemUserEntity = new ProblemUserEntity();
        problemUserEntity.setStatus(result);
        return problemUserMapper.updateByPidUid(pid, uid, problemUserEntity) == 1;
    }
}
