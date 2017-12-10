package org.inlighting.oj.web.service;

import org.apache.ibatis.session.SqlSession;
import org.inlighting.oj.judge.ResultEnum;
import org.inlighting.oj.web.dao.ProblemUserDao;
import org.inlighting.oj.web.entity.ProblemUserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Smith
 **/
@Service
public class ProblemUserService {

    private ProblemUserDao problemUserDao;

    private SqlSession sqlSession;

    public ProblemUserService(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    @Autowired
    public void setProblemUserDao(ProblemUserDao problemUserDao) {
        this.problemUserDao = problemUserDao;
    }

    public boolean add(int pid, int uid, ResultEnum status) {
        ProblemUserEntity problemUserEntity = new ProblemUserEntity();
        problemUserEntity.setPid(pid);
        problemUserEntity.setUid(uid);
        problemUserEntity.setStatus(status);
        return problemUserDao.add(sqlSession, problemUserEntity);
    }

    public ProblemUserEntity get(int pid, int uid) {
        ProblemUserEntity problemUserEntity = new ProblemUserEntity();
        problemUserEntity.setPid(pid);
        problemUserEntity.setUid(uid);
        return problemUserDao.get(sqlSession, problemUserEntity);
    }

    public boolean update(int pid, int uid, ResultEnum result) {
        ProblemUserEntity problemUserEntity = new ProblemUserEntity();
        problemUserEntity.setPid(pid);
        problemUserEntity.setUid(uid);
        problemUserEntity.setStatus(result);
        return problemUserDao.update(sqlSession, problemUserEntity);
    }
}
