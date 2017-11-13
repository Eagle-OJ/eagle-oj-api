package org.inlighting.oj.web.service;

import com.alibaba.fastjson.JSONArray;
import org.apache.ibatis.session.SqlSession;
import org.inlighting.oj.judge.config.LanguageEnum;
import org.inlighting.oj.judge.config.ProblemStatusEnum;
import org.inlighting.oj.web.dao.ProblemInfoDao;
import org.inlighting.oj.web.dao.SubmissionDao;
import org.inlighting.oj.web.dao.UserDao;
import org.inlighting.oj.web.entity.SubmissionEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Smith
 **/
@Service
public class SubmissionService {

    private final SqlSession sqlSession;

    private SubmissionDao submissionDao;

    private ProblemInfoDao problemInfoDao;

    private UserDao userDao;

    public SubmissionService(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    @Autowired
    public void setSubmissionDao(SubmissionDao submissionDao) {
        this.submissionDao = submissionDao;
    }

    @Autowired
    public void setProblemInfoDao(ProblemInfoDao problemInfoDao) {
        this.problemInfoDao = problemInfoDao;
    }

    @Autowired
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Transactional
    public boolean submitCodeInGlobal(boolean isOfficial, int owner, int pid, int codeSource, LanguageEnum codeLanguage,
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
        boolean submission = submissionDao.insert(sqlSession, submissionEntity);
        boolean submitTimes = true;
        boolean acceptTimes = true;
        if (isOfficial) {
            submitTimes = userDao.addUserSubmitTimesByUid(sqlSession, owner);
            // 答案正确，添加acceptTimes
            if (statusEnum == ProblemStatusEnum.Accepted) {
                acceptTimes = userDao.addUserAcceptTimesByUid(sqlSession, owner);
            }
        }

        return submission && submitTimes && acceptTimes;
    }
}
