package org.inlighting.oj.web.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageRowBounds;
import org.apache.ibatis.session.SqlSession;
import org.inlighting.oj.judge.JudgeHelper;
import org.inlighting.oj.web.dao.ProblemDao;
import org.inlighting.oj.web.dao.ContestProblemDao;
import org.inlighting.oj.web.entity.ProblemEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ygj
 **/
@Service
public class ProblemService {

    private final SqlSession sqlSession;

    private ProblemDao problemDao;

    private ContestProblemDao problemInfoDao;

    public ProblemService(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    @Autowired
    public void setProblemDao(ProblemDao problemDao) {
        this.problemDao = problemDao;
    }

    @Autowired
    public void setProblemInfoDao(ContestProblemDao problemInfoDao) {
        this.problemInfoDao = problemInfoDao;
    }


    public int addProblem(int owner, String title, JSONObject description, JSONObject inputFormat, JSONObject outputFormat,
                          int difficult, JSONArray samples, int time, int memory) {
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

        return problemDao.addProblem(sqlSession, problemEntity)? problemEntity.getPid() : 0;
    }

    public List<ProblemEntity> getProblemsByUid(int uid, PageRowBounds page) {
        return problemDao.getProblemsByUid(sqlSession, uid, page);
    }

    public ProblemEntity getProblemByPid(int pid) {
        return problemDao.getProblemByPid(sqlSession, pid);
    }

    public List<Map<String, Object>> getProblemTags(int pid) {
        return problemDao.getProblemTags(sqlSession, pid);
    }

    public List<Map<String, Object>> getSharedProblems(int difficult, String tag, PageRowBounds pager) {
        Map<String, Object> condition = new HashMap<>(2);
        condition.put("difficult", difficult);
        condition.put("tag", tag);
        return problemDao.getSharedProblems(sqlSession, condition, pager);
    }

    public List<Map<String, Object>> getSharedProblemsWithStatus(int uid, int difficult, String tag, PageRowBounds pager) {
        Map<String, Object> condition = new HashMap<>(3);
        condition.put("uid", uid);
        condition.put("difficult", difficult);
        condition.put("tag", tag);
        return problemDao.getSharedProblemsWithStatus(sqlSession, condition, pager);
    }

    public boolean updateProblemDescription(int pid, String title, JSONObject description, JSONObject inputFormat,
                                            JSONObject outputFormat, JSONArray samples, int difficult) {
        //通过pid来更新题目
        ProblemEntity problemEntity = new ProblemEntity();
        problemEntity.setPid(pid);
        problemEntity.setTitle(title);
        problemEntity.setDescription(description);
        problemEntity.setInputFormat(inputFormat);
        problemEntity.setOutputFormat(outputFormat);
        problemEntity.setSamples(samples);
        problemEntity.setDifficult(difficult);
        return problemDao.updateProblemDescription(sqlSession, problemEntity);
    }

    public boolean updateProblemSetting(int pid, JSONArray lang, int time, int memory, int status) {
        ProblemEntity problemEntity = new ProblemEntity();
        problemEntity.setPid(pid);
        problemEntity.setLang(lang);
        problemEntity.setTime(time);
        problemEntity.setMemory(memory);
        problemEntity.setStatus(status);
        return problemDao.updateProblemSetting(sqlSession, problemEntity);
    }

    public boolean updateProblemTimes(int pid, ProblemEntity problemEntity) {
        problemEntity.setPid(pid);
        return problemDao.updateProblemTimes(sqlSession, problemEntity);
    }
}
