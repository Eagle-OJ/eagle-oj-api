package org.inlighting.oj.web.service;

import org.apache.ibatis.session.SqlSession;
import org.inlighting.oj.web.dao.ProblemModeratorDao;
import org.inlighting.oj.web.entity.ProblemModeratorEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author Smith
 **/
@Service
public class ProblemModeratorService {

    private SqlSession sqlSession;

    private ProblemModeratorDao problemModeratorDao;

    public ProblemModeratorService(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    @Autowired
    public void setProblemModeratorDao(ProblemModeratorDao problemModeratorDao) {
        this.problemModeratorDao = problemModeratorDao;
    }

    public boolean add(int pid, int uid) {
        ProblemModeratorEntity entity = new ProblemModeratorEntity();
        entity.setUid(uid);
        entity.setPid(pid);
        return problemModeratorDao.add(sqlSession, entity);
    }

    public boolean isExist(int pid, int uid) {
        return get(pid, uid) != null;
    }

    public ProblemModeratorEntity get(int pid, int uid) {
        ProblemModeratorEntity entity = new ProblemModeratorEntity();
        entity.setPid(pid);
        entity.setUid(uid);
        return problemModeratorDao.get(sqlSession, entity);
    }

    public List<Map<String, Object>> getWithUser(int pid) {
        return problemModeratorDao.selectWithUser(sqlSession, pid);
    }

    public boolean delete(int pid, int uid) {
        ProblemModeratorEntity entity = new ProblemModeratorEntity();
        entity.setPid(pid);
        entity.setUid(uid);
        return problemModeratorDao.delete(sqlSession, entity);
    }
}
