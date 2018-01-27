package com.eagleoj.web.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.eagleoj.judge.JudgeHelper;
import com.eagleoj.web.controller.exception.WebErrorException;
import com.eagleoj.web.dao.ProblemMapper;
import com.eagleoj.web.dao.TagProblemMapper;
import com.eagleoj.web.dao.TagsMapper;
import com.eagleoj.web.entity.ProblemEntity;
import com.eagleoj.web.entity.TagProblemEntity;
import com.eagleoj.web.service.ProblemService;
import com.eagleoj.web.service.TagProblemService;
import com.eagleoj.web.service.TagsService;
import com.eagleoj.web.util.WebUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Smith
 **/
@Service
public class ProblemServiceImpl implements ProblemService {

    @Autowired
    private ProblemMapper problemMapper;

    @Autowired
    private TagsService tagsService;

    @Autowired
    private TagProblemService tagProblemService;

    @Transactional
    @Override
    public int save(JSONArray tags, int owner, String title, JSONObject description,
                    JSONObject inputFormat, JSONObject outputFormat,
                          int difficult, JSONArray samples, int time, int memory) {
        // 保存tag标签并且添加tag标签使用次数
        List<Integer> tagList = new ArrayList<>(tags.size());
        for(int i=0; i<tags.size(); i++) {
            int tid = tags.getInteger(i);
            if (tagsService.addUsedTimes(tid)) {
                tagList.add(tid);
            }
        }
        if (tagList.size() == 0) {
            throw new WebErrorException("标签非法");
        }

        // 添加题目
        ProblemEntity problemEntity = new ProblemEntity();
        problemEntity.setOwner(owner);
        problemEntity.setTitle(title);
        problemEntity.setLang(JudgeHelper.getAllLanguages());
        problemEntity.setDescription(description);
        problemEntity.setInputFormat(inputFormat);
        problemEntity.setOutputFormat(outputFormat);
        problemEntity.setDifficult(difficult);
        problemEntity.setSamples(samples);
        problemEntity.setTime(time);
        problemEntity.setMemory(memory);
        problemEntity.setStatus(0);
        problemEntity.setCreateTime(System.currentTimeMillis());
        boolean flag = problemMapper.save(problemEntity) == 1;
        if (! flag) {
            throw new WebErrorException("题目添加失败");
        }
        int pid = problemEntity.getPid();

        // 添加pid和tag之间的关联
        for (Integer i: tagList) {
            tagProblemService.saveProblemTag(i, pid);
        }
        return pid;
    }

    @Transactional
    @Override
    public void updateProblem(int pid, JSONArray newTags, ProblemEntity problemEntity) {
        if (newTags != null) {
            // tags 过滤
            List<Integer> originTags= tagProblemService.getProblemTags(pid)
                    .stream()
                    .map(TagProblemEntity::getTid).collect(Collectors.toList());
            List<Integer> finalTags = new ArrayList<>(newTags.size());

            for(int i=0; i<newTags.size(); i++) {
                int tid = newTags.getInteger(i);
                if (originTags.contains(tid)) {
                    finalTags.add(tid);
                } else {
                    if (tagsService.addUsedTimes(tid)) {
                        finalTags.add(tid);
                    }
                }
            }

            if (finalTags.size() == 0) {
                throw new WebErrorException("非法标签");
            }
            // 删除旧标签
            tagProblemService.deleteProblemTags(pid);
            // 添加pid和tag之间的关联
            for (Integer i: finalTags) {
                tagProblemService.saveProblemTag(i, pid);
            }
        }
        boolean flag = problemMapper.updateByPid(pid, problemEntity) == 1;
        if (! flag) {
            throw new WebErrorException("题目更新失败");
        }
    }

    @Override
    public void updateProblem(int pid, ProblemEntity problemEntity) {
        updateProblem(pid, null, problemEntity);
    }

    @Override
    public List<ProblemEntity> listUserProblems(int uid) {
        return problemMapper.listByUid(uid);
    }

    @Override
    public List<ProblemEntity> listAuditingProblems() {
        return problemMapper.listAuditing();
    }

    @Override
    public boolean refuseProblem(int pid) {
        return problemMapper.refuseByPid(pid) == 1;
    }

    @Override
    public boolean acceptProblem(int pid) {
        return problemMapper.acceptByPid(pid) == 1;
    }

    @Override
    public ProblemEntity getProblem(int pid) {
        ProblemEntity problemEntity = problemMapper.getByPid(pid);
        WebUtil.assertNotNull(problemEntity, "不存在此题目");
        return problemEntity;
    }

    @Override
    public List<Map<String, Object>> listProblemTags(int pid) {
        return problemMapper.listProblemTagsByPid(pid);
    }

    @Override
    public List<Map<String, Object>> listSharedProblems(String tag, Integer difficult, Integer uid) {
        return problemMapper.listShared(uid, difficult, tag);
    }
}
