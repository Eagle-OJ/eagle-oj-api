package org.inlighting.oj.web.service;

import org.apache.ibatis.session.SqlSession;
import org.inlighting.oj.web.dao.ContestProblemDao;
import org.inlighting.oj.web.entity.ContestProblemEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Smith
 **/
@Service
public class ContestProblemService {

    @Autowired
    private ContestProblemDao contestProblemDao;

    @Autowired
    private SqlSession sqlSession;

    public ContestProblemEntity getContestProblem(int pid, int cid) {
        ContestProblemEntity contestProblemEntity = new ContestProblemEntity();
        contestProblemEntity.setPid(pid);
        contestProblemEntity.setCid(cid);
        return contestProblemDao.getContestProblem(sqlSession, contestProblemEntity);
    }

    public List<HashMap<String, Object>> getContestProblems(int cid) {
        return contestProblemDao.getContestProblems(sqlSession, cid);
    }

    public List<HashMap<String, Object>> getContestProblemsWithStatus(int cid, int uid) {
        Map<String, Object> condition = new HashMap<>(2);
        condition.put("cid", cid);
        condition.put("uid", uid);
        return contestProblemDao.getContestProblemsWithStatus(sqlSession, condition);
    }

    public boolean addProblemInfo(int pid, int cid, int displayId, int score) {
        ContestProblemEntity contestProblemEntity = new ContestProblemEntity();
        contestProblemEntity.setPid(pid);
        contestProblemEntity.setCid(cid);
        contestProblemEntity.setDisplayId(displayId);
        contestProblemEntity.setScore(score);
        return contestProblemDao.add(sqlSession, contestProblemEntity);
    }

    public boolean updateContestProblem(int cid, int pid, int displayId, int score) {
        ContestProblemEntity entity = new ContestProblemEntity();
        entity.setCid(cid);
        entity.setPid(pid);
        entity.setDisplayId(displayId);
        entity.setScore(score);
        return contestProblemDao.updateContestProblem(sqlSession, entity);
    }

    public boolean updateContestProblemTimes(int cid, int pid, ContestProblemEntity entity) {
        entity.setCid(cid);
        entity.setPid(pid);
        return contestProblemDao.updateContestProblemTimes(sqlSession, entity);
    }

    public boolean deleteContestProblem(int cid, int pid) {
        ContestProblemEntity entity = new ContestProblemEntity();
        entity.setCid(cid);
        entity.setPid(pid);
        return contestProblemDao.deleteContestProblem(sqlSession, entity);
    }

    public boolean displayIdIsDuplicate(int cid, int displayId) {
        ContestProblemEntity entity = new ContestProblemEntity();
        entity.setCid(cid);
        entity.setDisplayId(displayId);
        ContestProblemEntity result = contestProblemDao.getContestProblem(sqlSession, entity);
        return result != null;
    }


}
