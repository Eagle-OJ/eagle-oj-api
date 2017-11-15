package org.inlighting.oj.web.service;

import com.alibaba.fastjson.JSONArray;
import org.apache.ibatis.session.SqlSession;
import org.inlighting.oj.web.dao.ProblemDao;
import org.inlighting.oj.web.dao.ProblemContestInfoDao;
import org.inlighting.oj.web.entity.ProblemEntity;
import org.inlighting.oj.web.entity.ProblemContestInfoEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author ygj
 **/
@Service
public class ProblemService {

    private final SqlSession sqlSession;

    private ProblemDao problemDao;

    private ProblemContestInfoDao problemInfoDao;

    public ProblemService(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    @Autowired
    public void setProblemDao(ProblemDao problemDao) {
        this.problemDao = problemDao;
    }

    @Autowired
    public void setProblemInfoDao(ProblemContestInfoDao problemInfoDao) {
        this.problemInfoDao = problemInfoDao;
    }


    public int addProblem(int owner, JSONArray codeLanguage, String title,
                              String description, int difficult, String inputFormat,
                              String outputFormat, String constraint, JSONArray sample,
                              JSONArray moderator, JSONArray tag, int share,long createTime) {
        // 添加题目
        ProblemEntity problemEntity = new ProblemEntity();
        problemEntity.setOwner(owner);
        problemEntity.setCodeLanguage(codeLanguage);
        problemEntity.setTitle(title);
        problemEntity.setDescription(description);
        problemEntity.setDifficult(difficult);
        problemEntity.setInputFormat(inputFormat);
        problemEntity.setOutputFormat(outputFormat);
        problemEntity.setConstraint(constraint);
        problemEntity.setSample(sample);
        problemEntity.setModerator(moderator);
        problemEntity.setTag(tag);
        problemEntity.setShare(share);
        problemEntity.setCreateTime(createTime);

        return problemEntity.getPid();
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

    public boolean updateProblemByPid(int pid, JSONArray codeLanguage, String title,
                                     String description, int difficult, String inputFormat,
                                     String outputFormat, String constraint, JSONArray sample,
                                     JSONArray moderator, JSONArray tag, int share) {
        //通过pid来更新题目
        ProblemEntity problemEntity = new ProblemEntity();
        problemEntity.setPid(pid);
        problemEntity.setCodeLanguage(codeLanguage);
        problemEntity.setTitle(title);
        problemEntity.setDescription(description);
        problemEntity.setDifficult(difficult);
        problemEntity.setInputFormat(inputFormat);
        problemEntity.setOutputFormat(outputFormat);
        problemEntity.setConstraint(constraint);
        problemEntity.setModerator(moderator);
        problemEntity.setSample(sample);
        problemEntity.setTag(tag);
        problemEntity.setShare(share);
        problemEntity.setCreateTime(System.currentTimeMillis());
        return problemDao.updateProblemByPid(sqlSession, problemEntity);
    }

    public boolean addProblemSubmitTimes(int pid) {
        return problemDao.addProblemSubmitTimes(sqlSession, pid);
    }

    public boolean addProblemAcceptTimes(int pid) {
        return problemDao.addProblemAcceptTimes(sqlSession, pid);
    }
}
