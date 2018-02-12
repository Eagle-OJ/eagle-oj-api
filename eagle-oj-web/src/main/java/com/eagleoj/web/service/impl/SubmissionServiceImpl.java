package com.eagleoj.web.service.impl;

import com.eagleoj.judge.LanguageEnum;
import com.eagleoj.judge.ResultEnum;
import com.eagleoj.web.dao.SubmissionMapper;
import com.eagleoj.web.entity.SubmissionEntity;
import com.eagleoj.web.service.SubmissionService;
import com.eagleoj.web.util.WebUtil;
import com.github.pagehelper.PageRowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Smith
 **/
@Service
public class SubmissionServiceImpl implements SubmissionService {

    @Autowired
    private SubmissionMapper submissionMapper;

    @Override
    public void save(int owner, int pid, int cid, int gid, int sourceCode, LanguageEnum lang, double time, int memory,
                   ResultEnum status) {
        SubmissionEntity submissionEntity = new SubmissionEntity();
        submissionEntity.setOwner(owner);
        submissionEntity.setPid(pid);
        submissionEntity.setCid(cid);
        submissionEntity.setGid(gid);
        submissionEntity.setSourceCode(sourceCode);
        submissionEntity.setLang(lang);
        submissionEntity.setTime(time);
        submissionEntity.setMemory(memory);
        submissionEntity.setStatus(status);
        submissionEntity.setSubmitTime(System.currentTimeMillis());
        boolean flag = submissionMapper.save(submissionEntity) == 1;
        WebUtil.assertIsSuccess(flag, "代码提交记录保存失败");
    }

    @Override
    public int countProblemSubmissions(int pid) {
        return submissionMapper.countByPid(pid);
    }

    @Override
    public List<Map<String, Object>> listOwnSubmissions(Integer owner, Integer pid, Integer cid) {
        return submissionMapper.listSubmissionsByOwnerPidCid(owner, pid, cid);
    }

    @Override
    public List<Map<String, Object>> listProblemSubmissions(Integer pid, Integer cid) {
        return submissionMapper.listSubmissionsByOwnerPidCid(null, pid, cid);
    }
}
