package com.eagleoj.web.service.impl;

import com.eagleoj.judge.ResultEnum;
import com.eagleoj.web.dao.ProblemUserMapper;
import com.eagleoj.web.entity.ProblemUserEntity;
import com.eagleoj.web.service.ProblemUserService;
import com.eagleoj.web.util.WebUtil;
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
    public void save(int pid, int uid, ResultEnum status) {
        ProblemUserEntity problemUserEntity = new ProblemUserEntity();
        problemUserEntity.setPid(pid);
        problemUserEntity.setUid(uid);
        problemUserEntity.setStatus(status);
        boolean flag = problemUserMapper.save(problemUserEntity) == 1;
        WebUtil.assertIsSuccess(flag, "用户做题记录保存失败");
    }

    @Override
    public ProblemUserEntity get(int pid, int uid) {
        ProblemUserEntity problemUserEntity = problemUserMapper.getByPidUid(pid, uid);
        WebUtil.assertNotNull(problemUserEntity, "没有该记录");
        return problemUserEntity;
    }

    @Override
    public List<Map<String, Object>> listUserProblemHistory(int uid) {
        return problemUserMapper.listUserProblemsByUid(uid);
    }

    @Override
    public void updateByPidUid(int pid, int uid, ResultEnum result) {
        ProblemUserEntity problemUserEntity = new ProblemUserEntity();
        problemUserEntity.setStatus(result);
        boolean flag =  problemUserMapper.updateByPidUid(pid, uid, problemUserEntity) == 1;
        WebUtil.assertIsSuccess(flag, "用户做题记录更新失败");
    }
}
