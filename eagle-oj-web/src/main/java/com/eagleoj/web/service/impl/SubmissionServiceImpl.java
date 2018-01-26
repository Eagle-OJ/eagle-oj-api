package com.eagleoj.web.service.impl;

import com.eagleoj.judge.LanguageEnum;
import com.eagleoj.judge.ResultEnum;
import com.eagleoj.web.dao.SubmissionMapper;
import com.eagleoj.web.entity.SubmissionEntity;
import com.eagleoj.web.service.SubmissionService;
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
    public int save(int owner, int pid, int cid, int sourceCode, LanguageEnum lang, double time, int memory,
                   ResultEnum status) {
        SubmissionEntity submissionEntity = new SubmissionEntity();
        submissionEntity.setOwner(owner);
        submissionEntity.setPid(pid);
        submissionEntity.setCid(cid);
        submissionEntity.setSourceCode(sourceCode);
        submissionEntity.setLang(lang);
        submissionEntity.setTime(time);
        submissionEntity.setMemory(memory);
        submissionEntity.setStatus(status);
        submissionEntity.setSubmitTime(System.currentTimeMillis());
        boolean flag = submissionMapper.save(submissionEntity) == 1;
        return flag? submissionEntity.getSid(): 0;
    }

    @Override
    public List<Map<String, Object>> listSubmissions(int owner, int pid, int cid) {
        return submissionMapper.listSubmissionsByOwnerPidCid(owner, pid, cid);
    }
}
