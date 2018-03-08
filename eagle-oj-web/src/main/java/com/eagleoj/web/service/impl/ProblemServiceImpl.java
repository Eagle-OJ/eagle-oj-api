package com.eagleoj.web.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.eagleoj.judge.JudgeHelper;
import com.eagleoj.web.controller.exception.WebErrorException;
import com.eagleoj.web.dao.ProblemMapper;
import com.eagleoj.web.dao.TagProblemMapper;
import com.eagleoj.web.dao.TagsMapper;
import com.eagleoj.web.data.status.ProblemStatus;
import com.eagleoj.web.entity.ProblemEntity;
import com.eagleoj.web.entity.TagEntity;
import com.eagleoj.web.entity.TagProblemEntity;
import com.eagleoj.web.entity.TestCaseEntity;
import com.eagleoj.web.service.*;
import com.eagleoj.web.service.async.AsyncTaskService;
import com.eagleoj.web.util.WebUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Smith
 **/
@Service
public class ProblemServiceImpl implements ProblemService {

    private Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ProblemMapper problemMapper;

    @Autowired
    private TagsService tagsService;

    @Autowired
    private TagProblemService tagProblemService;

    @Autowired
    private AsyncTaskService asyncTaskService;

    @Autowired
    private SubmissionService submissionService;

    @Autowired
    private ContestProblemService contestProblemService;

    @Autowired
    private ProblemModeratorService problemModeratorService;

    @Autowired
    private TestCasesService testCasesService;

    @Transactional
    @Override
    public int save(JSONArray tags, int owner, String title, JSONObject description,
                    JSONObject inputFormat, JSONObject outputFormat,
                    int difficult, JSONArray samples, int time, int memory, ProblemStatus status) {
        // 保存tag标签并且添加tag标签使用次数
        List<Integer> tagList = new ArrayList<>(tags.size());
        for(int i=0; i<tags.size(); i++) {
            int tid = tags.getInteger(i);
            tagsService.addUsedTimes(tid);
            tagList.add(tid);
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
        problemEntity.setStatus(status.getNumber());
        problemEntity.setCreateTime(System.currentTimeMillis());
        boolean flag = problemMapper.save(problemEntity) == 1;
        WebUtil.assertIsSuccess(flag, "题目添加失败");
        int pid = problemEntity.getPid();

        // 添加pid和tag之间的关联
        for (Integer i: tagList) {
            tagProblemService.saveProblemTag(i, pid);
        }
        return pid;
    }

    @Override
    public int countProblems() {
        return problemMapper.count();
    }

    @Override
    public int countAuditingProblems() {
        return problemMapper.countAuditing();
    }

    @Override
    public int getRandomPid() {
        return problemMapper.getRandomPid();
    }

    @Transactional
    @Override
    public void deleteProblem(int pid) {

        // 检查题目是否有提交记录
        int submissions = submissionService.countProblemSubmissions(pid);
        if (submissions > 0) {
            throw new WebErrorException("该题目已有人提交，无法删除");
        }
        // 检查题目是否有被比赛使用
        int contestUsed = contestProblemService.countContestProblems(pid);
        if (contestUsed > 0) {
            throw new WebErrorException("该题目已被比赛调用，无法删除");
        }

        // 删除problem-tag
        tagProblemService.deleteProblemTags(pid);
        // 删除problem-moderator
        if (problemModeratorService.countProblemModerators(pid) > 0) {
            problemModeratorService.deleteModerators(pid);
        }
        // 删除problem-testCases
        if (testCasesService.countProblemTestCases(pid) > 0) {
            testCasesService.deleteProblemTestCases(pid);
        }
        // 删除problem
        boolean flag = problemMapper.deleteByPid(pid) == 1;
        WebUtil.assertIsSuccess(flag, "删除题目失败");
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
                    tagsService.addUsedTimes(tid);
                    finalTags.add(tid);
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
        WebUtil.assertIsSuccess(flag, "题目更新失败");
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

    @Transactional
    @Override
    public void acceptProblem(ProblemEntity problemEntity) {
        WebUtil.assertIsSuccess(problemMapper.acceptByPid(problemEntity.getPid()) == 1, "审核失败");
        asyncTaskService.sendProblemAuditingMessage(problemEntity.getTitle(), problemEntity.getOwner(), problemEntity.getPid(), true);
    }

    @Transactional
    @Override
    public void refuseProblem(ProblemEntity problemEntity) {
        WebUtil.assertIsSuccess(problemMapper.refuseByPid(problemEntity.getPid()) == 1, "审核失败");
        asyncTaskService.sendProblemAuditingMessage(problemEntity.getTitle(), problemEntity.getOwner(), problemEntity.getPid(), false);
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
    public List<Map<String, Object>> listSharedProblems(String tag, Integer difficult, Integer uid, String query) {
        return problemMapper.listShared(uid, difficult, tag, query);
    }

    @Override
    public List<ProblemEntity> listProblemsForContest(int uid, String query) {
        return problemMapper.listProblemsForContest(uid, query);
    }

    @Override
    public List<ProblemEntity> listAllProblems() {
        return problemMapper.listAll();
    }

    @Override
    public boolean exportProblems(JSONArray pidList) {
        JSONArray data = new JSONArray();
        for (int i=0; i<pidList.size(); i++) {
            int pid = pidList.getInteger(i);
            JSONObject obj = new JSONObject();
            // add problem
            ProblemEntity problemEntity = problemMapper.getByPid(pid);
            obj.put("title", problemEntity.getTitle());
            obj.put("description", problemEntity.getDescription());
            obj.put("input_format", problemEntity.getInputFormat());
            obj.put("output_format", problemEntity.getOutputFormat());
            obj.put("difficult", problemEntity.getDifficult());
            obj.put("samples", problemEntity.getSamples());
            obj.put("time", problemEntity.getTime());
            obj.put("memory", problemEntity.getMemory());
            // add test cases
            List<TestCaseEntity> testCases = testCasesService.listProblemTestCases(pid);
            JSONArray testCaseArray = new JSONArray(testCases.size());
            for (TestCaseEntity entity: testCases) {
                JSONObject tempObj = new JSONObject();
                tempObj.put("stdin", entity.getStdin());
                tempObj.put("stdout", entity.getStdout());
                tempObj.put("strength", entity.getStrength());
                testCaseArray.add(tempObj);
            }
            obj.put("test_cases", testCaseArray);
            // add tags
            List<Map<String, Object>> tagsList = listProblemTags(pid);
            JSONArray tags = new JSONArray(tagsList.size());
            for (Map<String, Object> tag: tagsList) {
                tags.add(tag.get("name"));
            }
            obj.put("tags", tags);
            data.add(obj);
        }
        FileOutputStream fileOutputStream = null;
        try {
            Calendar calendar = Calendar.getInstance();
            String filename = calendar.get(Calendar.YEAR)+"-"
                    +calendar.get(Calendar.MONTH)+"-"
                    +calendar.get(Calendar.DAY_OF_MONTH)+"-"
                    +calendar.get(Calendar.HOUR_OF_DAY)+"-"
                    +calendar.get(Calendar.MINUTE)+"-"
                    +calendar.get(Calendar.SECOND)+".json";

            File parent = new File("data");
            if (! parent.exists()) {
                if (! parent.mkdir()) {
                    throw new IOException("create dictionary failed");
                }
            }

            File file = new File("data/"+filename);
            if (! file.exists()) {
                if (! file.createNewFile()) {
                    throw new IOException("create file failed");
                }
            }
            fileOutputStream = new FileOutputStream(file, false);
            fileOutputStream.write(data.toJSONString().getBytes("utf-8"));
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
            return false;
        } finally {
            try {
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
            } catch (IOException e) {
                LOGGER.error(e.getMessage());
            }
        }
        return true;
    }

    @Transactional
    @Override
    public boolean importProblems(int uid, JSONArray list) {
        for (int i=0; i<list.size(); i++) {
            JSONObject obj = list.getJSONObject(i);
            JSONArray tags = obj.getJSONArray("tags");
            JSONArray tagsList = new JSONArray(tags.size());
            for (int j=0; j<tags.size(); j++) {
                String tagName = tags.getString(j);
                TagEntity tagEntity = null;
                try {
                    tagEntity = tagsService.getByName(tagName);
                } catch (Exception e) {}
                if (tagEntity == null) {
                    tagsList.add(tagsService.save(tagName));
                } else {
                    tagsList.add(tagEntity.getTid());
                }
            }

            String title = obj.getString("title");
            JSONObject description = obj.getJSONObject("description");
            JSONObject inputFormat = obj.getJSONObject("input_format");
            JSONObject outputFormat = obj.getJSONObject("output_format");
            int difficult = obj.getInteger("difficult");
            JSONArray samples = obj.getJSONArray("samples");
            int time = obj.getInteger("time");
            int memory = obj.getInteger("memory");
            int pid = save(tagsList, uid, title, description, inputFormat, outputFormat, difficult, samples, time, memory, ProblemStatus.SHARING);
            // save test cases
            JSONArray testCases = obj.getJSONArray("test_cases");
            for (int j=0; j<testCases.size(); j++) {
                JSONObject testCase = testCases.getJSONObject(j);
                testCasesService.save(pid, testCase.getString("stdin"), testCase.getString("stdout"),
                        testCase.getInteger("strength"));
            }
        }
        return true;
    }
}
