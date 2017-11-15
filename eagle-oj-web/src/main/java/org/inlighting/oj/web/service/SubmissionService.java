package org.inlighting.oj.web.service;

import com.alibaba.fastjson.JSONArray;
import org.apache.ibatis.session.SqlSession;
import org.inlighting.oj.judge.config.CodeLanguageEnum;
import org.inlighting.oj.judge.config.ProblemStatusEnum;
import org.inlighting.oj.web.dao.ProblemContestInfoDao;
import org.inlighting.oj.web.dao.SubmissionDao;
import org.inlighting.oj.web.dao.UserDao;
import org.inlighting.oj.web.entity.SubmissionEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Smith
 **/
@Service
public class SubmissionService {

    private final SqlSession sqlSession;

    private SubmissionDao submissionDao;

    private ProblemContestInfoDao problemInfoDao;

    private UserDao userDao;

    public SubmissionService(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    @Autowired
    public void setSubmissionDao(SubmissionDao submissionDao) {
        this.submissionDao = submissionDao;
    }

    @Autowired
    public void setProblemInfoDao(ProblemContestInfoDao problemInfoDao) {
        this.problemInfoDao = problemInfoDao;
    }

    @Autowired
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public int submitCode(int owner, int pid, int codeSource, CodeLanguageEnum codeLanguage,
                              int belong, JSONArray result, double timeUsed, double memoryUsed,
                              ProblemStatusEnum statusEnum, long submitTime) {
        // 添加submission
        SubmissionEntity submissionEntity = new SubmissionEntity();
        submissionEntity.setOwner(owner);
        submissionEntity.setPid(pid);
        submissionEntity.setCodeSource(codeSource);
        submissionEntity.setCodeLanguage(codeLanguage);
        submissionEntity.setBelong(belong);
        submissionEntity.setResult(result);
        submissionEntity.setTimeUsed(timeUsed);
        submissionEntity.setMemoryUsed(memoryUsed);
        submissionEntity.setStatus(statusEnum);
        submissionEntity.setSubmitTime(submitTime);
        return submissionDao.insert(sqlSession, submissionEntity) ? submissionEntity.getSid(): 0;
    }
}
