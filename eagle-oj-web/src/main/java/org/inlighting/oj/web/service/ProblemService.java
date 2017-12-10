package org.inlighting.oj.web.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageRowBounds;
import org.apache.ibatis.session.SqlSession;
import org.inlighting.oj.web.dao.ProblemDao;
import org.inlighting.oj.web.dao.ContestProblemDao;
import org.inlighting.oj.web.entity.ProblemEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
                          int difficult, JSONArray samples, long createTime) {
        // 添加题目
        ProblemEntity problemEntity = new ProblemEntity();
        problemEntity.setOwner(owner);
        problemEntity.setTitle(title);
        problemEntity.setCodeLanguage(new JSONArray());
        problemEntity.setDescription(description);
        problemEntity.setInputFormat(inputFormat);
        problemEntity.setOutputFormat(outputFormat);
        problemEntity.setDifficult(difficult);
        problemEntity.setSamples(samples);
        problemEntity.setModerators(new JSONArray());
        problemEntity.setStatus(0);
        problemEntity.setCreateTime(createTime);

        return problemDao.addProblem(sqlSession, problemEntity)? problemEntity.getPid() : 0;
    }

    public List<ProblemEntity> getProblemsByUid(int uid, PageRowBounds page) {
        return problemDao.getProblemsByUid(sqlSession, uid, page);
    }

    /**
     * 添加problem和contest之间的关系
     * 开启事务
     */
    @Transactional
    public boolean addContestProblem(List<Integer> problemList, int belong) {
        // todo
        return false;
    }

    public ProblemEntity getProblemByPid(int pid) {
        // 通过ID获得题目
        return problemDao.getProblemByPid(sqlSession, pid);
    }

    public List<Map<String, Object>> getProblemTags(int pid) {
        return problemDao.getProblemTags(sqlSession, pid);
    }

    public List<ProblemEntity> getSharedProblems(PageRowBounds pager) {
        return problemDao.getSharedProblems(sqlSession, pager);
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
        //problemEntity.setTags(tags);
        return problemDao.updateProblemDescription(sqlSession, problemEntity);
    }

    public boolean updateProblemStatus(int pid, int status) {
        ProblemEntity problemEntity = new ProblemEntity();
        problemEntity.setPid(pid);
        problemEntity.setStatus(status);
        return problemDao.updateProblemStatus(sqlSession, problemEntity);
    }

    public boolean updateProblemModerators(int pid, JSONArray moderators) {
        ProblemEntity problemEntity = new ProblemEntity();
        problemEntity.setPid(pid);
        problemEntity.setModerators(moderators);
        return problemDao.updateModerators(sqlSession, problemEntity);
    }

    public boolean updateProblemTimes(ProblemEntity problemEntity) {
        return problemDao.updateProblemTimes(sqlSession, problemEntity);
    }
}
